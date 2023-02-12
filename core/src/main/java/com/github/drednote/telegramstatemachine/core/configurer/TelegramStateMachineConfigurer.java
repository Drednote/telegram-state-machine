package com.github.drednote.telegramstatemachine.core.configurer;

import com.github.drednote.telegramstatemachine.core.converter.SimpleTelegramUpdateToMessageConverter;
import com.github.drednote.telegramstatemachine.core.converter.TelegramUpdateToMessageConverter;
import com.github.drednote.telegramstatemachine.core.error.ErrorTelegramHandler;
import com.github.drednote.telegramstatemachine.core.error.LoggingErrorTelegramHandler;
import com.github.drednote.telegramstatemachine.core.monitor.LoggingTelegramStateMachineMonitor;
import com.github.drednote.telegramstatemachine.core.monitor.TelegramStateMachineMonitor;
import com.github.drednote.telegramstatemachine.core.persist.TelegramStateMachinePersister;

/**
 * Интерфейс с помощью которого можно настроить стейтмашину
 *
 * @param <S>
 */
public interface TelegramStateMachineConfigurer<S> {

  /**
   * Указать кастомный монитор
   *
   * @see LoggingTelegramStateMachineMonitor
   */
  TelegramStateMachineConfigurer<S> withMonitor(TelegramStateMachineMonitor<S> monitor);

  /**
   * Указать кастомный персистер
   *
   * @see com.github.drednote.telegramstatemachine.core.persist.InMemoryTelegramStateMachinePersister
   */
  TelegramStateMachineConfigurer<S> withPersister(TelegramStateMachinePersister<S> persister);

  /**
   * Указать кастомный конвертер
   *
   * @see SimpleTelegramUpdateToMessageConverter
   */
  TelegramStateMachineConfigurer<S> withConverter(TelegramUpdateToMessageConverter<S> converter);

  /**
   * Указать кастомный обработчик ошибок
   *
   * @see LoggingErrorTelegramHandler
   */
  TelegramStateMachineConfigurer<S> withErrorHandler(ErrorTelegramHandler handler);
}
