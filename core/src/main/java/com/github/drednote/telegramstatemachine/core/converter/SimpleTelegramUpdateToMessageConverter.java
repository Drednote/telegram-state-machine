package com.github.drednote.telegramstatemachine.core.converter;

import com.github.drednote.telegramstatemachine.core.context.TransitionContext;
import com.github.drednote.telegramstatemachine.message.Message;
import com.github.drednote.telegramstatemachine.message.SimpleMessage;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class SimpleTelegramUpdateToMessageConverter<S> implements
    TelegramUpdateToMessageConverter<S> {

  @Override
  public Message<S> convert(@Nullable Update update, TransitionContext<S> context) {
    return new SimpleMessage<>(
        context.machine().getId(), context.machine().getState(), context.stage(), update
    );
  }
}
