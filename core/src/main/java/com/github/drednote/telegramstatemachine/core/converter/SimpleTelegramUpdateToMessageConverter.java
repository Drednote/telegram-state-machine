package com.github.drednote.telegramstatemachine.core.converter;

import com.github.drednote.telegramstatemachine.core.context.TransitionContext;
import com.github.drednote.telegramstatemachine.message.Message;
import com.github.drednote.telegramstatemachine.message.SimpleMessage;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class SimpleTelegramUpdateToMessageConverter<S> implements
    TelegramUpdateToMessageConverter<S> {

  @Override
  public Message<S> convert(Update update, TransitionContext<S> context) {
    return new SimpleMessage<>(update, context.machine().getState(), context.stage());
  }
}
