package com.github.drednote.telegramstatemachine.exception.transition;

import com.github.drednote.telegramstatemachine.core.transition.Transition;
import java.util.List;

// todo make normal text
public class AmbitiousTransitionException extends TransitionException {

  public <S> AmbitiousTransitionException(List<Transition<S>> transitions) {
    super(
        "More than 1 target states. Abort changing state. Transitions: %s".formatted(transitions));
  }
}
