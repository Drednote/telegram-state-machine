package com.github.drednote.telegramstatemachine.exception.transition;

// todo make normal text
public class EmptyTransitionException extends TransitionException {

  public EmptyTransitionException() {
    super("Empty target states. Abort changing state");
  }
}
