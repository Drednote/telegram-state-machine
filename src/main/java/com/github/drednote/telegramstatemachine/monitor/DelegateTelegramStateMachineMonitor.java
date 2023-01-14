package com.github.drednote.telegramstatemachine.monitor;

import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DelegateTelegramStateMachineMonitor<S> implements TelegramStateMachineMonitor<S> {

  @Nullable
  private final TelegramStateMachineMonitor<S> delegate;

  @Override
  public void transition(Transition<S> transition) {
    if (delegate != null) {
      delegate.transition(transition);
    }
  }

  @Override
  public void create(TelegramStateMachine<S> machine) {
    if (delegate != null) {
      delegate.create(machine);
    }
  }

  @Override
  public void restore(TelegramStateMachine<S> machine) {
    if (delegate != null) {
      delegate.restore(machine);
    }
  }

  @Override
  public void prepare(TelegramStateMachine<S> machine) {
    if (delegate != null) {
      delegate.prepare(machine);
    }
  }
}
