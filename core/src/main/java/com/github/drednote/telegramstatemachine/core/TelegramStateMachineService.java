package com.github.drednote.telegramstatemachine.core;

import com.github.drednote.telegramstatemachine.exception.transition.TransitionException;
import javax.annotation.Nullable;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramStateMachineService<S> {

  TelegramStateMachine<S> start(String id) throws TransitionException;

  /**
   * Doesn't affect on persisted statemachine
   */
  @Nullable
  TelegramStateMachine<S> prepare(String id, Update update) throws TransitionException;

  boolean transit(String id, Update update);

  boolean transit(String id, S state);
}
