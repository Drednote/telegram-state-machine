package com.github.drednote.telegramstatemachine.core.transition;


import com.github.drednote.telegramstatemachine.core.transition.multistage.MultiStageTelegramStateMachineConfigurer;
import com.github.drednote.telegramstatemachine.core.transition.simple.SimpleTelegramStateMachineConfigurer;

public interface TelegramTransitionsStateMachineConfigurer<S> {

  SimpleTelegramStateMachineConfigurer<S> withSimple();

  MultiStageTelegramStateMachineConfigurer<S> withMultiStage();
}
