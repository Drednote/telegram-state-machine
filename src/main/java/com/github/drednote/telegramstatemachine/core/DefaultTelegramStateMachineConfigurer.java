package com.github.drednote.telegramstatemachine.core;

import com.github.drednote.telegramstatemachine.api.transition.SimpleTelegramStateMachineConfigurer;
import com.github.drednote.telegramstatemachine.api.transition.SimpleTelegramStateMachineConfigurerImpl;
import com.github.drednote.telegramstatemachine.api.transition.Transition;
import java.util.Collection;

public class DefaultTelegramStateMachineConfigurer<S> implements TelegramStateMachineConfigurer<S> {

  final Collection<Transition<S>> transitions;

  public DefaultTelegramStateMachineConfigurer(Collection<Transition<S>> transitions) {
    this.transitions = transitions;
  }

  @Override
  public SimpleTelegramStateMachineConfigurer<S> withSimple() {
    return new SimpleTelegramStateMachineConfigurerImpl<>(transitions);
  }
}
