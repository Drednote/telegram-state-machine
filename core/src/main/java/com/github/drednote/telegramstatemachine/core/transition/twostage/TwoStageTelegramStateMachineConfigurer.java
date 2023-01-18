package com.github.drednote.telegramstatemachine.core.transition.twostage;

import com.github.drednote.telegramstatemachine.core.transition.TransitionTelegramStateMachineConfigurer;

public interface TwoStageTelegramStateMachineConfigurer<S> extends
    TransitionTelegramStateMachineConfigurer<S, TwoStageTelegramStateMachineConfigurer<S>> {

  TwoStageTelegramStateMachineConfigurer<S> source(S source);

  /**
   * Промежуточный статус
   */
  TwoStageTelegramStateMachineConfigurer<S> dummy(S source);

  TwoStageTelegramStateMachineConfigurer<S> target(S target);

}
