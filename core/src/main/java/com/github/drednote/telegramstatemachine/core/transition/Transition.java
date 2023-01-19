package com.github.drednote.telegramstatemachine.core.transition;

import com.github.drednote.telegramstatemachine.core.error.ErrorTelegramHandler;
import com.github.drednote.telegramstatemachine.message.Message;
import com.github.drednote.telegramstatemachine.api.HandlerResponse;
import com.github.drednote.telegramstatemachine.exception.handler.HandlerException;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Transition<S> extends ErrorTelegramHandler {

  S source();

  HandlerResponse handle(Message<S> update) throws HandlerException;

  boolean matches(Update update);
}
