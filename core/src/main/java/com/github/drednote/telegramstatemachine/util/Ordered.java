package com.github.drednote.telegramstatemachine.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;

public interface Ordered {

  int getOrder();

  /**
   * Useful constant for the highest precedence value.
   *
   * @see java.lang.Integer#MIN_VALUE
   */
  int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

  /**
   * Useful constant for the lowest precedence value.
   *
   * @see java.lang.Integer#MAX_VALUE
   */
  int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

  class OrderComparator implements Comparator<Object> {

    /**
     * Shared default instance of {@code OrderComparator}.
     */
    public static final OrderComparator INSTANCE = new OrderComparator();


    /**
     * Build an adapted order comparator with the given source provider.
     *
     * @param sourceProvider the order source provider to use
     * @return the adapted comparator
     * @since 4.1
     */
    public Comparator<Object> withSourceProvider(OrderSourceProvider sourceProvider) {
      return (o1, o2) -> doCompare(o1, o2, sourceProvider);
    }

    @Override
    public int compare(@Nullable Object o1, @Nullable Object o2) {
      return doCompare(o1, o2, null);
    }

    private int doCompare(@Nullable Object o1, @Nullable Object o2,
        @Nullable OrderSourceProvider sourceProvider) {
      int i1 = getOrder(o1, sourceProvider);
      int i2 = getOrder(o2, sourceProvider);
      return Integer.compare(i1, i2);
    }

    /**
     * Determine the order value for the given object.
     * <p>The default implementation checks against the given {@link OrderSourceProvider}
     * using {@link #findOrder} and falls back to a regular {@link #getOrder(Object)} call.
     *
     * @param obj the object to check
     * @return the order value, or {@code Ordered.LOWEST_PRECEDENCE} as fallback
     */
    private int getOrder(@Nullable Object obj, @Nullable OrderSourceProvider sourceProvider) {
      Integer order = null;
      if (obj != null && sourceProvider != null) {
        Object orderSource = sourceProvider.getOrderSource(obj);
        if (orderSource != null) {
          if (orderSource.getClass().isArray()) {
            for (Object source : toObjectArray(orderSource)) {
              order = findOrder(source);
              if (order != null) {
                break;
              }
            }
          } else {
            order = findOrder(orderSource);
          }
        }
      }
      return (order != null ? order : getOrder(obj));
    }

    /**
     * Determine the order value for the given object.
     * <p>The default implementation checks against the {@link Ordered} interface
     * through delegating to {@link #findOrder}. Can be overridden in subclasses.
     *
     * @param obj the object to check
     * @return the order value, or {@code Ordered.LOWEST_PRECEDENCE} as fallback
     */
    protected int getOrder(@Nullable Object obj) {
      if (obj != null) {
        Integer order = findOrder(obj);
        if (order != null) {
          return order;
        }
      }
      return Ordered.LOWEST_PRECEDENCE;
    }

    /**
     * Find an order value indicated by the given object.
     * <p>The default implementation checks against the {@link Ordered} interface.
     * Can be overridden in subclasses.
     *
     * @param obj the object to check
     * @return the order value, or {@code null} if none found
     */
    @Nullable
    protected Integer findOrder(Object obj) {
      return (obj instanceof Ordered ordered ? ordered.getOrder() : null);
    }


    /**
     * Sort the given List with a default OrderComparator.
     * <p>Optimized to skip sorting for lists with size 0 or 1,
     * in order to avoid unnecessary array extraction.
     *
     * @param list the List to sort
     * @see java.util.List#sort(java.util.Comparator)
     */
    public static void sort(List<?> list) {
      if (list.size() > 1) {
        list.sort(INSTANCE);
      }
    }

    /**
     * Sort the given array with a default OrderComparator.
     * <p>Optimized to skip sorting for lists with size 0 or 1,
     * in order to avoid unnecessary array extraction.
     *
     * @param array the array to sort
     * @see java.util.Arrays#sort(Object[], java.util.Comparator)
     */
    public static void sort(Object[] array) {
      if (array.length > 1) {
        Arrays.sort(array, INSTANCE);
      }
    }

    /**
     * Sort the given array or List with a default OrderComparator, if necessary. Simply skips
     * sorting when given any other value.
     * <p>Optimized to skip sorting for lists with size 0 or 1,
     * in order to avoid unnecessary array extraction.
     *
     * @param value the array or List to sort
     * @see java.util.Arrays#sort(Object[], java.util.Comparator)
     */
    public static void sortIfNecessary(Object value) {
      if (value instanceof Object[] arr) {
        sort(arr);
      } else if (value instanceof List) {
        sort((List<?>) value);
      }
    }

    /**
     * Convert the given array (which may be a primitive array) to an object array (if necessary of
     * primitive wrapper objects).
     * <p>A {@code null} source value will be converted to an
     * empty Object array.
     *
     * @param source the (potentially primitive) array
     * @return the corresponding object array (never {@code null})
     * @throws IllegalArgumentException if the parameter is not an array
     */
    public static Object[] toObjectArray(@Nullable Object source) {
      if (source instanceof Object[] objects) {
        return objects;
      }
      if (source == null) {
        return new Object[0];
      }
      if (!source.getClass().isArray()) {
        throw new IllegalArgumentException("Source is not an array: " + source);
      }
      int length = Array.getLength(source);
      if (length == 0) {
        return new Object[0];
      }
      Class<?> wrapperType = Array.get(source, 0).getClass();
      Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
      for (int i = 0; i < length; i++) {
        newArray[i] = Array.get(source, i);
      }
      return newArray;
    }


    /**
     * Strategy interface to provide an order source for a given object.
     *
     * @since 4.1
     */
    @FunctionalInterface
    public interface OrderSourceProvider {

      /**
       * Return an order source for the specified object, i.e. an object that should be checked for
       * an order value as a replacement to the given object.
       * <p>Can also be an array of order source objects.
       * <p>If the returned object does not indicate any order, the comparator
       * will fall back to checking the original object.
       *
       * @param obj the object to find an order source for
       * @return the order source for that object, or {@code null} if none found
       */
      @Nullable
      Object getOrderSource(Object obj);
    }

  }
}
