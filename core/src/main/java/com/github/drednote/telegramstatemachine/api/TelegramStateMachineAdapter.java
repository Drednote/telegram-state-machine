package com.github.drednote.telegramstatemachine.api;

import com.github.drednote.telegramstatemachine.core.configurer.TelegramStateMachineConfigurer;
import com.github.drednote.telegramstatemachine.core.transition.TelegramTransitionsStateMachineConfigurer;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface TelegramStateMachineAdapter<S> {

  default void onConfigure(TelegramStateMachineConfigurer<S> configurer) {
    // nothing to do
  }

  default void onTransitions(TelegramTransitionsStateMachineConfigurer<S> configurer) {
    // nothing to do
  }

  S initialState();

  AbsSender absSender();
}
