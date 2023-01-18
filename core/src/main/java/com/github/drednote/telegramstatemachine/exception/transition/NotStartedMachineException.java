package com.github.drednote.telegramstatemachine.exception.transition;

public class NotStartedMachineException extends TransitionException {

  public NotStartedMachineException(String id) {
    super("Machine with id '%s' not started".formatted(id));
  }
}
