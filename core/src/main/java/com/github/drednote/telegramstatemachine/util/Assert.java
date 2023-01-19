package com.github.drednote.telegramstatemachine.util;

public interface Assert {

  static void notNull(Object object, String text) {
    if (object == null) {
      throw new IllegalArgumentException(text);
    }
  }

  static void positive(Number number, String text) {
    if (number.intValue() <= 0) {
      throw new IllegalArgumentException(text);
    }
  }

  static void moreThan(Number number, int value, String text) {
    if (number.intValue() <= value) {
      throw new IllegalArgumentException(text);
    }
  }
}
