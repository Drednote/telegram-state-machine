package com.github.drednote.telegramstatemachine.core.persist;

import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;

/**
 * Персистер для сохранения сущностей
 *
 * @param <S>
 */
public interface TelegramStateMachinePersister<S> {

  TelegramStateMachine<S> persist(TelegramStateMachine<S> stateMachine);

  TelegramStateMachine<S> get(String id);

  boolean contains(String id);
}
