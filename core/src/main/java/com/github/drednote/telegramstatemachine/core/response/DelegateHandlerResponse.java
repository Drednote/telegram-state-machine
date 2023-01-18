package com.github.drednote.telegramstatemachine.core.response;

import java.util.Arrays;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RequiredArgsConstructor
public class DelegateHandlerResponse implements HandlerResponse {

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
