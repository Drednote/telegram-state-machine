package com.github.drednote.telegramstatemachine.core.transition.multistage;

import com.github.drednote.telegramstatemachine.api.UpdateTelegramHandler;
import com.github.drednote.telegramstatemachine.core.error.ErrorTelegramHandler;
import com.github.drednote.telegramstatemachine.core.transition.AbstractTransition;
import com.github.drednote.telegramstatemachine.matcher.Matcher;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MultiStageTransition<S> extends AbstractTransition<S> {

  private final S source;
  @Getter
  private final int count;
  @Getter
  private final S target;

  public MultiStageTransition(
      S source, int count, S target, UpdateTelegramHandler<S> handler,
      ErrorTelegramHandler errorHandler, Matcher<Update> matcher
  ) {
    super(handler, errorHandler, matcher);
    this.source = source;
    this.count = count;
    this.target = target;
  }

  @Override
  public S source() {
    return source;
  }
}
