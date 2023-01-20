package com.github.drednote.telegramstatemachine.core.transition.twostage;

import com.github.drednote.telegramstatemachine.api.UpdateTelegramHandler;
import com.github.drednote.telegramstatemachine.core.error.ErrorTelegramHandler;
import com.github.drednote.telegramstatemachine.core.transition.AbstractTransition;
import com.github.drednote.telegramstatemachine.matcher.Matcher;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TwoStageTransition<S> extends AbstractTransition<S> {

  private final S source;
  @Getter
  private final S dummy;
  @Getter
  private final S target;

  TwoStageTransition(
      S source, S dummy, S target, UpdateTelegramHandler<S> handler,
      ErrorTelegramHandler errorHandler, Matcher<Update> matcher
  ) {
    super(handler, errorHandler, matcher);
    this.source = source;
    this.dummy = dummy;
    this.target = target;
  }

  @Override
  public S source() {
    return source;
  }
}
