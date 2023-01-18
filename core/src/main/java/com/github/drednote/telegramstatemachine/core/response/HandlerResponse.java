package com.github.drednote.telegramstatemachine.core.response;

import com.github.drednote.telegramstatemachine.util.Ordered;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface HandlerResponse extends Ordered {

  void process(AbsSender absSender) throws TelegramApiException;

  static HandlerResponse create(HandlerResponse... responses) {
    if (responses == null || responses.length == 0) {
      throw new IllegalArgumentException("Handler responses must be >= 0");
    }
    return new DelegateHandlerResponse(responses);
  }

  @Override
  default int getOrder() {
    return 0;
  }
}
