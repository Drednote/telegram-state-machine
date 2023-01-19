package com.github.drednote.telegramstatemachine.core.transition;

import com.github.drednote.telegramstatemachine.api.UpdateTelegramHandler;
import com.github.drednote.telegramstatemachine.core.error.ErrorTelegramHandler;
import com.github.drednote.telegramstatemachine.matcher.Matcher;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TransitionTelegramStateMachineConfigurer<S, T extends TransitionTelegramStateMachineConfigurer<S, T>> {

  T source(S source);

  T target(S target);

  T handler(UpdateTelegramHandler<S> handler, ErrorTelegramHandler errorHandler);

  /**
   * Using global ErrorTelegramHandler
   */
  T handler(UpdateTelegramHandler<S> handler);

  T matcher(Matcher<Update> matcher);

  TelegramTransitionsStateMachineConfigurer<S> and();
}
