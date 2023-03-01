package com.github.drednote.telegramstatemachine.core.converter;

import com.github.drednote.telegramstatemachine.core.context.TransitionContext;
import com.github.drednote.telegramstatemachine.message.Message;
import javax.annotation.Nullable;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramUpdateToMessageConverter<S> {

  Message<S> convert(@Nullable Update update, TransitionContext<S> machine);
}
