package com.github.drednote.telegramstatemachine.core.transition;

import com.github.drednote.telegramstatemachine.api.UpdateTelegramHandler;
import com.github.drednote.telegramstatemachine.core.error.ErrorTelegramHandler;
import com.github.drednote.telegramstatemachine.api.HandlerResponse;
import com.github.drednote.telegramstatemachine.exception.handler.HandlerException;
import com.github.drednote.telegramstatemachine.matcher.Matcher;
import com.github.drednote.telegramstatemachine.message.Message;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractTransition<S> implements Transition<S> {

  protected final UpdateTelegramHandler<S> handler;
  protected final ErrorTelegramHandler errorHandler;
  protected final Matcher<Update> matcher;

  @Override
  public HandlerResponse handle(Message<S> update) throws HandlerException {
    return handler.onUpdate(update);
  }

  @Override
  public boolean matches(Update update) {
    return matcher.match(update);
  }

  @Override
  public void onApiError(TelegramApiException exception, AbsSender absSender) {
    errorHandler.onApiError(exception, absSender);
  }

  @Override
  public void onHandleError(HandlerException exception, AbsSender absSender) {
    errorHandler.onHandleError(exception, absSender);
  }
}
