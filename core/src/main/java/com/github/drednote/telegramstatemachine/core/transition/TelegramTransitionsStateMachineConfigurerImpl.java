package com.github.drednote.telegramstatemachine.core.transition;

import com.github.drednote.telegramstatemachine.core.error.ErrorTelegramHandler;
import com.github.drednote.telegramstatemachine.core.transition.multistage.MultiStageTelegramStateMachineConfigurer;
import com.github.drednote.telegramstatemachine.core.transition.multistage.MultiStageTelegramStateMachineConfigurerImpl;
import com.github.drednote.telegramstatemachine.core.transition.simple.SimpleTelegramStateMachineConfigurer;
import com.github.drednote.telegramstatemachine.core.transition.simple.SimpleTelegramStateMachineConfigurerImpl;
import com.github.drednote.telegramstatemachine.util.Assert;
import java.util.Collection;
import lombok.Getter;

public class TelegramTransitionsStateMachineConfigurerImpl<S> implements
    TelegramTransitionsStateMachineConfigurer<S> {

  @Getter
  private final Collection<Transition<S>> transitions;
  private final ErrorTelegramHandler errorHandler;

  public TelegramTransitionsStateMachineConfigurerImpl(
      Collection<Transition<S>> transitions, ErrorTelegramHandler handler
  ) {
    Assert.notNull(transitions, "'transitions' must not be null");
    Assert.notNull(handler, "'errorHandler' must not be null");
    this.transitions = transitions;
    this.errorHandler = handler;
  }

  @Override
  public SimpleTelegramStateMachineConfigurer<S> withSimple() {
    return new SimpleTelegramStateMachineConfigurerImpl<>(transitions, errorHandler);
  }

  @Override
  public MultiStageTelegramStateMachineConfigurer<S> withMultiStage() {
    return new MultiStageTelegramStateMachineConfigurerImpl<>(transitions, errorHandler);
  }
}
