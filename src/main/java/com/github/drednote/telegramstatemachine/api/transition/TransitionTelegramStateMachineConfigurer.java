package com.github.drednote.telegramstatemachine.api.transition;

import com.github.drednote.telegramstatemachine.api.UpdateTelegramHandler;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachineConfigurer;
import com.github.drednote.telegramstatemachine.matcher.UpdateMatcher;

public interface TransitionTelegramStateMachineConfigurer<S,
    T extends TransitionTelegramStateMachineConfigurer<S, T>> {

  T source(S source);

  T target(S target);

  T onUpdate(UpdateTelegramHandler handler);

  T matcher(UpdateMatcher matcher);

  TelegramStateMachineConfigurer<S> and();
}
