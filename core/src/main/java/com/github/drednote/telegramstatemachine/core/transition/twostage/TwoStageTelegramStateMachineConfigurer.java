package com.github.drednote.telegramstatemachine.core.transition.twostage;

import com.github.drednote.telegramstatemachine.core.transition.TransitionTelegramStateMachineConfigurer;

/**
 * unused
 *
 * @param <S>
 */
public interface TwoStageTelegramStateMachineConfigurer<S> extends
    TransitionTelegramStateMachineConfigurer<S, TwoStageTelegramStateMachineConfigurer<S>> {

  /**
   * Промежуточный статус
   */
  TwoStageTelegramStateMachineConfigurer<S> dummy(S source);

}
