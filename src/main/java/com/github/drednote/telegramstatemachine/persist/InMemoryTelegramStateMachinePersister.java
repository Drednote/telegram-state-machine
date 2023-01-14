package com.github.drednote.telegramstatemachine.persist;

import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTelegramStateMachinePersister<S> implements TelegramStateMachinePersister<S> {

  private final Map<String, TelegramStateMachine<S>> machineMap = new ConcurrentHashMap<>();

  @Override
  public TelegramStateMachine<S> persist(TelegramStateMachine<S> stateMachine) {
    machineMap.put(stateMachine.getId(), stateMachine);
    return get(stateMachine.getId());
  }

  @Override
  public TelegramStateMachine<S> restore(TelegramStateMachine<S> stateMachine) {
    String id = stateMachine.getId();
    TelegramStateMachine<S> machine = machineMap.get(id);
    return machine != null ? machine : stateMachine;
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
