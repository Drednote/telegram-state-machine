package com.github.drednote.telegramstatemachine.core.monitor;

import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;

public interface TelegramStateMachineMonitor<S> {

  void transition(MonitorTransition<S> monitorTransition);

  void create(TelegramStateMachine<S> machine);

  void restore(TelegramStateMachine<S> machine);

  void prepare(TelegramStateMachine<S> machine);
}
