package com.github.drednote.telegramstatemachine.api;

import com.github.drednote.telegramstatemachine.core.TelegramStateMachineConfigurer;

public interface TelegramStateMachineAdapter<S> {

  void onConfigure(TelegramStateMachineConfigurer<S> configurer);

  S initialState();
}
