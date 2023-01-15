package com.github.drednote.telegramstatemachine.exception;

import com.github.drednote.telegramstatemachine.api.transition.Transition;
import java.util.List;

public class AmbitiousTransitionException extends TransitionException {

  public <S> AmbitiousTransitionException(List<Transition<S>> transitions) {
    super(
        "More than 1 target states. Abort changing state. Transitions: %s".formatted(transitions));
  }
}
