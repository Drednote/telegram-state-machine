package com.github.drednote.telegramstatemachine.api.transition;

import com.github.drednote.telegramstatemachine.api.UpdateTelegramHandler;
import com.github.drednote.telegramstatemachine.core.DefaultTelegramStateMachineConfigurer;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachineConfigurer;
import com.github.drednote.telegramstatemachine.matcher.UpdateMatcher;
import java.util.Collection;

public class SimpleTelegramStateMachineConfigurerImpl<S> implements
    SimpleTelegramStateMachineConfigurer<S> {

  private final Collection<Transition<S>> transitions;
  private UpdateTelegramHandler handler;
  private UpdateMatcher matcher;
  private S source;
  private S target;

  public SimpleTelegramStateMachineConfigurerImpl(Collection<Transition<S>> transitions) {
    this.transitions = transitions;
  }

  @Override
  public SimpleTelegramStateMachineConfigurer<S> source(S source) {
    this.source = source;
    return this;
  }

  @Override
  public SimpleTelegramStateMachineConfigurer<S> target(S target) {
    this.target = target;
    return this;
  }

  @Override
  public SimpleTelegramStateMachineConfigurer<S> onUpdate(UpdateTelegramHandler handler) {
    this.handler = handler;
    return this;
  }

  @Override
  public SimpleTelegramStateMachineConfigurer<S> matcher(UpdateMatcher matcher) {
    this.matcher = matcher;
    return this;
  }

  @Override
  public TelegramStateMachineConfigurer<S> and() {
    transitions.add(new Transition<>(source, target, handler, matcher));
    return new DefaultTelegramStateMachineConfigurer<>(transitions);
  }
}
