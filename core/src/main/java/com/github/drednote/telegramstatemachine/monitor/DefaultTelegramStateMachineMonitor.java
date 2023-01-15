package com.github.drednote.telegramstatemachine.monitor;

import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultTelegramStateMachineMonitor<S> implements TelegramStateMachineMonitor<S> {

  @Override
  public void transition(Transition<S> transition) {
    String id = transition.id();
    S from = transition.from();
    S to = transition.to();
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
