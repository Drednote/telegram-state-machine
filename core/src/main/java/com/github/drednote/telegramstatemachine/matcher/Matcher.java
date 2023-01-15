package com.github.drednote.telegramstatemachine.matcher;


public interface Matcher<T> {

  boolean match(T t);

  default Matcher<T> and(Matcher<? super T> other) {
    if (other == null) {
      throw new IllegalArgumentException("Other matcher must not be null");
    }
    return t -> match(t) && other.match(t);
  }

  default Matcher<T> or(Matcher<? super T> other) {
    if (other == null) {
      throw new IllegalArgumentException("Other matcher must not be null");
    }
    return t -> match(t) || other.match(t);
  }

}
