package com.github.drednote.telegramstatemachine.core.persist;

import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;
import com.github.drednote.telegramstatemachine.util.Assert;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTelegramStateMachinePersister<S> implements TelegramStateMachinePersister<S> {

  private final Map<String, TelegramStateMachine<S>> machineMap = new ConcurrentHashMap<>();

  @Override
  public TelegramStateMachine<S> persist(TelegramStateMachine<S> stateMachine) {
    Assert.notNull(stateMachine, "'statemachine' must not be null");
    machineMap.put(stateMachine.getId(), stateMachine);
    return get(stateMachine.getId());
  }

  @Override
  public TelegramStateMachine<S> get(String id) {
    return machineMap.get(id);
  }

  @Override
  public boolean contains(String id) {
    return machineMap.containsKey(id);
  }
}
