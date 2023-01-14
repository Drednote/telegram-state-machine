package com.github.drednote.telegramstatemachine.monitor;

import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;

public interface TelegramStateMachineMonitor<S> {

  void transition(Transition<S> transition);

  void create(TelegramStateMachine<S> machine);

  void restore(TelegramStateMachine<S> machine);

  void prepare(TelegramStateMachine<S> machine);
}
