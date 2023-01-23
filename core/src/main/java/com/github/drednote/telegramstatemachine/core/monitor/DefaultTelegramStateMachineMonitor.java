package com.github.drednote.telegramstatemachine.core.monitor;

import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultTelegramStateMachineMonitor<S> implements TelegramStateMachineMonitor<S> {

  @Override
  public void transition(MonitorTransition<S> monitorTransition) {
    String id = monitorTransition.id();
    S from = monitorTransition.from();
    S to = monitorTransition.to();
    log.info("For machine with id {} make transition from {} to {}", id, from, to);
  }

  @Override
  public void create(TelegramStateMachine<S> machine) {
    log.info("Create new machine {}", machine);
  }

  @Override
  public void restore(TelegramStateMachine<S> machine) {
    log.info("Restore machine {}", machine);
  }

  @Override
  public void prepare(TelegramStateMachine<S> machine) {
    log.info("Prepare machine {}", machine);
  }
}
