package com.github.drednote.telegramstatemachine.exception;

public abstract class TransitionException extends Exception {

  protected TransitionException(String message) {
    super(message);
  }

  protected TransitionException(String message, Throwable cause) {
    super(message, cause);
  }
}
