package com.github.drednote.telegramstatemachine.core.transition;


import com.github.drednote.telegramstatemachine.core.transition.twostage.TwoStageTelegramStateMachineConfigurer;
import com.github.drednote.telegramstatemachine.core.transition.simple.SimpleTelegramStateMachineConfigurer;

public interface TelegramTransitionsStateMachineConfigurer<S> {

  SimpleTelegramStateMachineConfigurer<S> withSimple();

  TwoStageTelegramStateMachineConfigurer<S> withTwoStage();
}
