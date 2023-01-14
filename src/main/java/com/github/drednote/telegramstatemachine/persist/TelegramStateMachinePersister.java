package com.github.drednote.telegramstatemachine.persist;

import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;

public interface TelegramStateMachinePersister<S> {

  TelegramStateMachine<S> persist(TelegramStateMachine<S> stateMachine);

  TelegramStateMachine<S> restore(TelegramStateMachine<S> stateMachine);

  TelegramStateMachine<S> get(String id);

  boolean contains(String id);
}
