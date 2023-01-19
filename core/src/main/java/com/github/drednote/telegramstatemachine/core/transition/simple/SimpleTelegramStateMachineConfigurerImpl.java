package com.github.drednote.telegramstatemachine.core.transition.simple;

import com.github.drednote.telegramstatemachine.api.UpdateTelegramHandler;
import com.github.drednote.telegramstatemachine.core.error.ErrorTelegramHandler;
import com.github.drednote.telegramstatemachine.core.transition.TelegramTransitionsStateMachineConfigurerImpl;
import com.github.drednote.telegramstatemachine.core.transition.TelegramTransitionsStateMachineConfigurer;
import com.github.drednote.telegramstatemachine.core.transition.Transition;
import com.github.drednote.telegramstatemachine.matcher.Matcher;
import com.github.drednote.telegramstatemachine.util.Assert;
import java.util.Collection;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SimpleTelegramStateMachineConfigurerImpl<S> implements
    SimpleTelegramStateMachineConfigurer<S> {

  private final Collection<Transition<S>> transitions;
  private final ErrorTelegramHandler defaultErrorHandler;

  private UpdateTelegramHandler<S> handler;
  private ErrorTelegramHandler errorHandler;
  private Matcher<Update> matcher;
  private S source;
  private S target;

  public SimpleTelegramStateMachineConfigurerImpl(
      Collection<Transition<S>> transitions, ErrorTelegramHandler handler
  ) {
    this.transitions = transitions;
    this.defaultErrorHandler = handler;
    this.errorHandler = handler;
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
  public SimpleTelegramStateMachineConfigurer<S> handler(UpdateTelegramHandler<S> handler,
      ErrorTelegramHandler errorHandler) {
    this.handler = handler;
    this.errorHandler = errorHandler;
    return this;
  }

  @Override
  public SimpleTelegramStateMachineConfigurer<S> handler(UpdateTelegramHandler<S> handler) {
    this.handler = handler;
    return this;
  }

  @Override
  public SimpleTelegramStateMachineConfigurer<S> matcher(Matcher<Update> matcher) {
    this.matcher = matcher;
    return this;
  }

  @Override
  public TelegramTransitionsStateMachineConfigurer<S> and() {
    Assert.notNull(source, "'source' must be set");
    Assert.notNull(target, "'target' must be set");
    Assert.notNull(handler, "'handler' must be set");
    Assert.notNull(errorHandler, "'errorHandler' must be set");
    Assert.notNull(matcher, "'matcher' must be set");

    transitions.add(new SimpleTransition<>(source, target, handler, errorHandler, matcher));
    return new TelegramTransitionsStateMachineConfigurerImpl<>(transitions, defaultErrorHandler);
  }
}
