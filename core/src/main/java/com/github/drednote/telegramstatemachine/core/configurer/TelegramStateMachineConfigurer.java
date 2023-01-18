package com.github.drednote.telegramstatemachine.core.configurer;

import com.github.drednote.telegramstatemachine.core.error.ErrorTelegramHandler;
import com.github.drednote.telegramstatemachine.core.converter.TelegramUpdateToMessageConverter;
import com.github.drednote.telegramstatemachine.core.monitor.TelegramStateMachineMonitor;
import com.github.drednote.telegramstatemachine.core.persist.TelegramStateMachinePersister;

public interface TelegramStateMachineConfigurer<S> {

  TelegramStateMachineConfigurer<S> withMonitor(TelegramStateMachineMonitor<S> monitor);

  TelegramStateMachineConfigurer<S> withPersister(TelegramStateMachinePersister<S> persister);

  TelegramStateMachineConfigurer<S> withConverter(TelegramUpdateToMessageConverter<S> converter);

  TelegramStateMachineConfigurer<S> withErrorHandler(ErrorTelegramHandler handler);
}
