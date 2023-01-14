package com.github.drednote.telegramstatemachine.core;


import com.github.drednote.telegramstatemachine.api.transition.SimpleTelegramStateMachineConfigurer;

public interface TelegramStateMachineConfigurer<S> {

  SimpleTelegramStateMachineConfigurer<S> withSimple();
}
