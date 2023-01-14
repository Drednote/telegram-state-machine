package com.github.drednote.telegramstatemachine.core;

import com.github.drednote.telegramstatemachine.api.TelegramStateMachineAdapter;
import com.github.drednote.telegramstatemachine.api.transition.Transition;
import com.github.drednote.telegramstatemachine.exception.AmbitiousTransitionException;
import com.github.drednote.telegramstatemachine.exception.EmptyTransitionException;
import com.github.drednote.telegramstatemachine.exception.TransitionException;
import com.github.drednote.telegramstatemachine.monitor.DefaultTransition;
import com.github.drednote.telegramstatemachine.monitor.TelegramStateMachineMonitor;
import com.github.drednote.telegramstatemachine.persist.TelegramStateMachinePersister;
import com.github.drednote.telegramstatemachine.util.UpdateUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public abstract class DefaultTelegramStateMachineService<S> implements
    TelegramStateMachineService<S> {

  protected final TelegramStateMachineAdapter<S> adapter;
  protected final TelegramStateMachinePersister<S> persister;
  protected final TelegramStateMachineMonitor<S> monitor;

  private final Map<S, Collection<Transition<S>>> transitions;

  // fix test
  protected DefaultTelegramStateMachineService(
      TelegramStateMachineAdapter<S> adapter, TelegramStateMachinePersister<S> persister,
      TelegramStateMachineMonitor<S> monitor) {
    this.adapter = adapter;
    this.persister = persister;
    this.monitor = monitor;

    this.transitions = collectConfigs();
  }

  protected TelegramStateMachine<S> internalStart(String id) {
    if (persister.contains(id)) {
      TelegramStateMachine<S> machine = persister.get(id);
      monitor.restore(machine);
      return machine;
    }
    var machine = new DefaultTelegramStateMachine<>(id, adapter.initialState(), null);
    TelegramStateMachine<S> persist = persister.persist(machine);
    monitor.create(persist);
    return persist;
  }

  protected TelegramStateMachine<S> internalPrepare(Update update) throws TransitionException {
    String id = UpdateUtils.extractId(update);
    TelegramStateMachine<S> machine = persister.get(id);
    S curState = machine.getState();
    List<Transition<S>> matchTransitions = this.transitions.get(curState)
        .stream()
        .filter(transition -> transition.matcher().match(update))
        .toList();
    if (matchTransitions.isEmpty()) {
      throw new EmptyTransitionException();
    } else if (matchTransitions.size() > 1) {
      throw new AmbitiousTransitionException(matchTransitions);
    }
    var prepare = new DefaultTelegramStateMachine<>(id, curState, matchTransitions.get(0));
    monitor.prepare(prepare);
    return prepare;
  }

  protected boolean internalTransit(Update update) {
    try {
      TelegramStateMachine<S> machine = internalPrepare(update);
      S target = machine.getNext().target();
      var newMachine = new DefaultTelegramStateMachine<>(machine.getId(), target, null);
      persister.persist(newMachine);
      monitor.transition(new DefaultTransition<>(machine.getId(), machine.getState(), target));
      return true;
    } catch (TransitionException e) {
      log.warn("Cannot change status cause: ", e);
    }
    return false;
  }

  private Map<S, Collection<Transition<S>>> collectConfigs() {
    var configurer = new DefaultTelegramStateMachineConfigurer<S>(new ArrayList<>());
    adapter.onConfigure(configurer);
    Map<S, Collection<Transition<S>>> result = new HashMap<>();
    for (Transition<S> transition : configurer.transitions) {
      S source = transition.source();
      result.computeIfAbsent(source, s -> new ArrayList<>());
      result.get(source).add(transition);
    }
    return result;
  }
}
