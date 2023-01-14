package com.github.drednote.telegramstatemachine.core;

import com.github.drednote.telegramstatemachine.exception.TransitionException;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramStateMachineService<S> {

  TelegramStateMachine<S> start(String id) throws TransitionException;

  /**
   * Doesn't affect on persisted statemachine
   */
  TelegramStateMachine<S> prepare(Update update) throws TransitionException;

  boolean transit(Update update);
}
