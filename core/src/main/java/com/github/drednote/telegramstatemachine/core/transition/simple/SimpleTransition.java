package com.github.drednote.telegramstatemachine.core.transition.simple;

import com.github.drednote.telegramstatemachine.api.UpdateTelegramHandler;
import com.github.drednote.telegramstatemachine.core.error.ErrorTelegramHandler;
import com.github.drednote.telegramstatemachine.core.transition.AbstractTransition;
import com.github.drednote.telegramstatemachine.matcher.Matcher;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SimpleTransition<S> extends AbstractTransition<S> {

  private final S source;
  private final S target;

  SimpleTransition(
      S source, S target, UpdateTelegramHandler<S> handler,
      ErrorTelegramHandler errorHandler, Matcher<Update> matcher
  ) {
    super(handler, errorHandler, matcher);
    this.source = source;
    this.target = target;
  }

  @Override
  public S source() {
    return source;
  }

  @Override
  public S target() {
    return target;
  }
}
