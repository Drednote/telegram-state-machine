package com.github.drednote.telegramstatemachine.api;

import com.github.drednote.telegramstatemachine.util.Ordered;
import java.util.Arrays;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
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

  @RequiredArgsConstructor
  final class DelegateHandlerResponse implements HandlerResponse {

    private final Collection<HandlerResponse> handlerResponses;

    public DelegateHandlerResponse(HandlerResponse... handlerResponses) {
      this.handlerResponses = Arrays.stream(handlerResponses)
          .sorted(OrderComparator.INSTANCE)
          .toList();
    }

    @Override
    public void process(AbsSender absSender) throws TelegramApiException {
      for (HandlerResponse handlerResponse : handlerResponses) {
        handlerResponse.process(absSender);
      }
    }

  }
}
