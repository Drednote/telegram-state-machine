package com.github.drednote.telegramstatemachine.exception;

public class InternalTransitionException extends TransitionException {

  public InternalTransitionException(String message, Throwable cause) {
    super(message, cause);
  }

  public InternalTransitionException(String message) {
    super(message);
  }
}
