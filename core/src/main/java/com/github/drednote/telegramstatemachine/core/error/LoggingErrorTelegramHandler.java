package com.github.drednote.telegramstatemachine.core.error;

import com.github.drednote.telegramstatemachine.exception.handler.HandlerException;
import com.github.drednote.telegramstatemachine.exception.transition.TransitionException;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class LoggingErrorTelegramHandler implements ErrorTelegramHandler {

  @Override
  public void onHandleError(HandlerException exception, AbsSender absSender) {
    log.error("Handler error happened", exception);
  }

  @Override
  public void onApiError(TelegramApiException exception, AbsSender absSender) {
    log.error("Api error happened", exception);
  }

  @Override
  public void onTransitionError(TransitionException exception, AbsSender absSender) {
    log.warn("Cannot change status cause: ", exception);
  }
}
