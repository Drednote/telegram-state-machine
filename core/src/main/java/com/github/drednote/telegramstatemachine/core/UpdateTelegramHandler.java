package com.github.drednote.telegramstatemachine.core;

import com.github.drednote.telegramstatemachine.core.response.HandlerResponse;
import com.github.drednote.telegramstatemachine.exception.handler.HandlerException;
import com.github.drednote.telegramstatemachine.message.Message;

public interface UpdateTelegramHandler<S> {

  HandlerResponse onUpdate(Message<S> message) throws HandlerException;
}
