package com.github.drednote.telegramstatemachine.core.error;

import com.github.drednote.telegramstatemachine.exception.handler.HandlerException;
import com.github.drednote.telegramstatemachine.exception.transition.TransitionException;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface ErrorTelegramHandler {

  void onHandleError(HandlerException exception, AbsSender absSender);

  void onApiError(TelegramApiException exception, AbsSender absSender);

  void onTransitionError(TransitionException exception, AbsSender absSender);
}
