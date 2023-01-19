package com.github.drednote.telegramstatemachine.core.transition;


import com.github.drednote.telegramstatemachine.core.transition.multistage.MultiStageTelegramStateMachineConfigurer;
import com.github.drednote.telegramstatemachine.core.transition.simple.SimpleTelegramStateMachineConfigurer;
import com.github.drednote.telegramstatemachine.core.transition.twostage.TwoStageTelegramStateMachineConfigurer;

public interface TelegramTransitionsStateMachineConfigurer<S> {

  SimpleTelegramStateMachineConfigurer<S> withSimple();

  TwoStageTelegramStateMachineConfigurer<S> withTwoStage();

  MultiStageTelegramStateMachineConfigurer<S> withMultiStage();
}
