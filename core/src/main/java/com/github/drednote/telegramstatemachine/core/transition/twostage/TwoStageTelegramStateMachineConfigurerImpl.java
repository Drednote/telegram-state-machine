package com.github.drednote.telegramstatemachine.core.transition.twostage;

import com.github.drednote.telegramstatemachine.core.UpdateTelegramHandler;
import com.github.drednote.telegramstatemachine.core.error.ErrorTelegramHandler;
import com.github.drednote.telegramstatemachine.core.transition.TelegramTransitionsStateMachineConfigurerImpl;
import com.github.drednote.telegramstatemachine.core.transition.TelegramTransitionsStateMachineConfigurer;
import com.github.drednote.telegramstatemachine.core.transition.Transition;
import com.github.drednote.telegramstatemachine.matcher.Matcher;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class TwoStageTelegramStateMachineConfigurerImpl<S> implements
    TwoStageTelegramStateMachineConfigurer<S> {

  private final Collection<Transition<S>> transitions;
  private final ErrorTelegramHandler defaultErrorHandler;

  private UpdateTelegramHandler<S> handler;
  private ErrorTelegramHandler errorHandler;
  private Matcher<Update> matcher;

  private S source;
  private S dummy;
  private S target;

  @Override
  public TwoStageTelegramStateMachineConfigurer<S> source(S source) {
    this.source = source;
    return this;
  }

  @Override
  public TwoStageTelegramStateMachineConfigurer<S> dummy(S dummy) {
    this.dummy = dummy;
    return this;
  }

  @Override
  public TwoStageTelegramStateMachineConfigurer<S> target(S target) {
    this.target = target;
    return this;
  }

  @Override
  public TwoStageTelegramStateMachineConfigurer<S> handler(UpdateTelegramHandler<S> handler,
      ErrorTelegramHandler errorHandler) {
    this.handler = handler;
    this.errorHandler = errorHandler;
    return this;
  }

  @Override
  public TwoStageTelegramStateMachineConfigurer<S> handler(UpdateTelegramHandler<S> handler) {
    this.handler = handler;
    return this;
  }

  @Override
  public TwoStageTelegramStateMachineConfigurer<S> matcher(Matcher<Update> matcher) {
    this.matcher = matcher;
    return this;
  }

  @Override
  public TelegramTransitionsStateMachineConfigurer<S> and() {
    transitions.add(
        new TwoStageTransition<>(source, dummy, target, handler, errorHandler, matcher));
    return new TelegramTransitionsStateMachineConfigurerImpl<>(transitions, defaultErrorHandler);
  }
}
