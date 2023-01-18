package com.github.drednote.telegramstatemachine.core.converter;

import com.github.drednote.telegramstatemachine.message.Message;
import com.github.drednote.telegramstatemachine.message.SimpleMessage;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class DefaultTelegramUpdateToMessageConverter<S> implements
    TelegramUpdateToMessageConverter<S> {

  @Override
  public Message<S> convert(Update update, TelegramStateMachine<S> machine) {
    return new SimpleMessage<>(update, machine.getState());
  }
}
