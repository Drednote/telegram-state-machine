package com.github.drednote.telegramstatemachine.util;

public interface Assert {

  static void notNull(Object object, String text) {
    if (object == null) {
      throw new IllegalArgumentException(text);
    }
  }
}
