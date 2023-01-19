package com.github.drednote.telegramstatemachine.core.transition.simple;

import com.github.drednote.telegramstatemachine.core.transition.TransitionTelegramStateMachineConfigurer;

public interface SimpleTelegramStateMachineConfigurer<S> extends
    TransitionTelegramStateMachineConfigurer<S, SimpleTelegramStateMachineConfigurer<S>> {
}
