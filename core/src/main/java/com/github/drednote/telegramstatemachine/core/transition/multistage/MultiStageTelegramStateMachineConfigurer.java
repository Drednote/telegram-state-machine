package com.github.drednote.telegramstatemachine.core.transition.multistage;

import com.github.drednote.telegramstatemachine.core.transition.TransitionTelegramStateMachineConfigurer;
import javax.validation.constraints.Positive;

public interface MultiStageTelegramStateMachineConfigurer<S> extends
    TransitionTelegramStateMachineConfigurer<S, MultiStageTelegramStateMachineConfigurer<S>> {

  MultiStageTelegramStateMachineConfigurer<S> count(@Positive int count);

}
