package com.github.drednote.telegramstatemachine.core.converter;

import com.github.drednote.telegramstatemachine.message.Message;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramUpdateToMessageConverter<S> {

  Message<S> convert(Update update, TelegramStateMachine<S> machine);
}
