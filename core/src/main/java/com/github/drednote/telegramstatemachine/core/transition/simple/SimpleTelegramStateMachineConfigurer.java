package com.github.drednote.telegramstatemachine.core.transition.simple;

import com.github.drednote.telegramstatemachine.core.transition.TransitionTelegramStateMachineConfigurer;

public interface SimpleTelegramStateMachineConfigurer<S> extends
    TransitionTelegramStateMachineConfigurer<S, SimpleTelegramStateMachineConfigurer<S>> {

  SimpleTelegramStateMachineConfigurer<S> source(S source);

  SimpleTelegramStateMachineConfigurer<S> target(S target);
}
