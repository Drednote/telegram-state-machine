package com.github.drednote.telegramstatemachine.api;

import com.github.drednote.telegramstatemachine.exception.handler.HandlerException;
import com.github.drednote.telegramstatemachine.message.Message;

/**
 * Интерфейс описывающий работу при получении евента от бота
 *
 * @param <S>
 */
public interface UpdateTelegramHandler<S> {

  HandlerResponse onUpdate(Message<S> message) throws HandlerException;
}
