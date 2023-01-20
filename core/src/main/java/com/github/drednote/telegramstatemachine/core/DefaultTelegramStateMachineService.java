package com.github.drednote.telegramstatemachine.core;

import com.github.drednote.telegramstatemachine.api.HandlerResponse;
import com.github.drednote.telegramstatemachine.api.TelegramStateMachineAdapter;
import com.github.drednote.telegramstatemachine.core.configurer.TelegramStateMachineConfigurerImpl;
import com.github.drednote.telegramstatemachine.core.context.TransitionContext;
import com.github.drednote.telegramstatemachine.core.context.TransitionContextImpl;
import com.github.drednote.telegramstatemachine.core.converter.DefaultTelegramUpdateToMessageConverter;
import com.github.drednote.telegramstatemachine.core.converter.TelegramUpdateToMessageConverter;
import com.github.drednote.telegramstatemachine.core.error.DefaultErrorTelegramHandler;
import com.github.drednote.telegramstatemachine.core.error.ErrorTelegramHandler;
import com.github.drednote.telegramstatemachine.core.monitor.DefaultMonitorTransition;
import com.github.drednote.telegramstatemachine.core.monitor.DefaultTelegramStateMachineMonitor;
import com.github.drednote.telegramstatemachine.core.monitor.TelegramStateMachineMonitor;
import com.github.drednote.telegramstatemachine.core.persist.InMemoryTelegramStateMachinePersister;
import com.github.drednote.telegramstatemachine.core.persist.TelegramStateMachinePersister;
import com.github.drednote.telegramstatemachine.core.transition.TelegramTransitionsStateMachineConfigurerImpl;
import com.github.drednote.telegramstatemachine.core.transition.Transition;
import com.github.drednote.telegramstatemachine.core.transition.multistage.MultiStageTransition;
import com.github.drednote.telegramstatemachine.core.transition.simple.SimpleTransition;
import com.github.drednote.telegramstatemachine.core.transition.twostage.TwoStageTransition;
import com.github.drednote.telegramstatemachine.exception.handler.HandlerException;
import com.github.drednote.telegramstatemachine.exception.transition.AmbitiousTransitionException;
import com.github.drednote.telegramstatemachine.exception.transition.EmptyTransitionException;
import com.github.drednote.telegramstatemachine.exception.transition.NotStartedMachineException;
import com.github.drednote.telegramstatemachine.exception.transition.TransitionException;
import com.github.drednote.telegramstatemachine.util.Assert;
import com.github.drednote.telegramstatemachine.util.UpdateUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public abstract class DefaultTelegramStateMachineService<S> implements
    TelegramStateMachineService<S> {

  protected final TelegramStateMachineAdapter<S> adapter;
  protected final TelegramStateMachinePersister<S> persister;
  protected final TelegramStateMachineMonitor<S> monitor;
  protected final AbsSender absSender;
  protected final ErrorTelegramHandler errorHandler;
  protected final TelegramUpdateToMessageConverter<S> converter;

  private final Map<S, Collection<Transition<S>>> transitions;

  protected DefaultTelegramStateMachineService(TelegramStateMachineAdapter<S> adapter) {
    Assert.notNull(adapter, "TelegramStateMachineAdapter must not be null");

    var configurer = new TelegramStateMachineConfigurerImpl<S>();
    adapter.onConfigure(configurer);

    this.adapter = adapter;
    this.absSender = adapter.absSender();

    this.persister = Objects.requireNonNullElse(configurer.getPersister(),
        new InMemoryTelegramStateMachinePersister<>());
    this.monitor = Objects.requireNonNullElse(configurer.getMonitor(),
        new DefaultTelegramStateMachineMonitor<>());
    this.errorHandler = Objects.requireNonNullElse(configurer.getErrorHandler(),
        new DefaultErrorTelegramHandler());
    this.converter = Objects.requireNonNullElse(configurer.getConverter(),
        new DefaultTelegramUpdateToMessageConverter<>());

    this.transitions = collectConfigs();
  }

  protected TelegramStateMachine<S> internalStart(String id) {
    Assert.notNull(id, "'id' must not be null");

    if (persister.contains(id)) {
      TelegramStateMachine<S> machine = persister.get(id);
      monitor.restore(machine);
      return machine;
    }
    var machine = new DefaultTelegramStateMachine<>(id, adapter.initialState(), null, -1);
    TelegramStateMachine<S> persist = persister.persist(machine);
    monitor.create(persist);
    return persist;
  }

  protected TelegramStateMachine<S> internalPrepare(Update update) throws TransitionException {
    Assert.notNull(update, "'update' must not be null");

    String id = UpdateUtils.extractId(update);
    TelegramStateMachine<S> machine = persister.get(id);
    if (machine == null) {
      throw new NotStartedMachineException(id);
    }
    S curState = machine.getState();
    List<Transition<S>> matchTransitions = Optional.ofNullable(
            this.transitions.get(curState))
        .orElseThrow(EmptyTransitionException::new)
        .stream()
        .filter(transition -> transition.matches(update))
        .toList();
    if (matchTransitions.isEmpty()) {
      throw new EmptyTransitionException();
    } else if (matchTransitions.size() > 1) {
      throw new AmbitiousTransitionException(matchTransitions);
    }
    return new DefaultTelegramStateMachine<>(id, curState, matchTransitions.get(0),
        machine.getStage());
  }

  protected boolean internalTransit(Update update) {
    Assert.notNull(update, "'update' must not be null");
    try {
      TelegramStateMachine<S> machine = internalPrepare(update);
      monitor.prepare(machine);
      Transition<S> next = machine.getNext();
      determineTransition(update, machine, next);
      return true;
    } catch (TransitionException e) {
      log.warn("Cannot change status cause: ", e);
    }
    return false;
  }

  private void determineTransition(
      Update update, TelegramStateMachine<S> machine, Transition<S> next
  ) {
    try {
      if (next instanceof SimpleTransition<S> simpleTransition) {
        makeSimpleTransition(update, machine, simpleTransition);
      } else if (next instanceof TwoStageTransition<S> twoStageTransition) {
        makeTwoStageTransition(update, machine, twoStageTransition);
      } else if (next instanceof MultiStageTransition<S> multiStageTransition) {
        makeMultiStageTransition(update, machine, multiStageTransition);
      }
    } catch (TelegramApiException e) {
      next.onApiError(e, absSender);
    } catch (HandlerException e) {
      next.onHandleError(e, absSender);
    }
  }

  private void makeMultiStageTransition(
      Update update, TelegramStateMachine<S> machine,
      MultiStageTransition<S> next
  ) throws HandlerException, TelegramApiException {
    int stage = machine.getStage();
    int newStage = stage + 1 >= next.getCount() - 1 ? -1 : stage + 1;
    TransitionContext<S> context = new TransitionContextImpl<>(machine, stage + 1);
    HandlerResponse response = next.handle(converter.convert(update, context));
    response.process(absSender);
    makeTransition(machine,
        newStage == -1 ? next.getTarget() : machine.getState(),
        newStage
    );
  }

  private void makeSimpleTransition(
      Update update, TelegramStateMachine<S> machine,
      SimpleTransition<S> next
  ) throws HandlerException, TelegramApiException {
    TransitionContext<S> context = new TransitionContextImpl<>(machine, -1);
    HandlerResponse response = next.handle(converter.convert(update, context));
    response.process(absSender);
    makeTransition(machine, next.getTarget());
  }

  private void makeTwoStageTransition(
      Update update, TelegramStateMachine<S> machine,
      TwoStageTransition<S> next
  ) throws HandlerException, TelegramApiException {
    makeTransition(machine, next.getDummy());
    TransitionContext<S> context = new TransitionContextImpl<>(machine, -1);
    HandlerResponse response = next.handle(converter.convert(update, context));
    response.process(absSender);
    makeTransition(machine, next.getTarget());
  }

  private void makeTransition(TelegramStateMachine<S> machine, S target, int stage) {
    var newMachine = new DefaultTelegramStateMachine<>(machine.getId(), target, null, stage);
    persister.persist(newMachine);
    monitor.transition(new DefaultMonitorTransition<>(machine.getId(), machine.getState(), target));
  }

  private void makeTransition(TelegramStateMachine<S> machine, S target) {
    makeTransition(machine, target, -1);
  }

  private Map<S, Collection<Transition<S>>> collectConfigs() {
    var configurer = new TelegramTransitionsStateMachineConfigurerImpl<S>(new ArrayList<>(),
        errorHandler);
    adapter.onTransitions(configurer);
    Map<S, Collection<Transition<S>>> result = new HashMap<>();
    for (Transition<S> transition : configurer.getTransitions()) {
      S source = transition.source();
      result.computeIfAbsent(source, s -> new ArrayList<>());
      result.get(source).add(transition);
    }
    return result;
  }
}
