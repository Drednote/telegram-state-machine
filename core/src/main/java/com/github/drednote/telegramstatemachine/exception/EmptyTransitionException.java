package com.github.drednote.telegramstatemachine.exception;

public class EmptyTransitionException extends TransitionException {

  public EmptyTransitionException() {
    super("Empty target states. Abort changing state");
  }
}
