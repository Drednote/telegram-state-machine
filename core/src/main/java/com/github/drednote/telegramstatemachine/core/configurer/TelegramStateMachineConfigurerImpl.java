package com.github.drednote.telegramstatemachine.core.configurer;

import com.github.drednote.telegramstatemachine.core.error.ErrorTelegramHandler;
import com.github.drednote.telegramstatemachine.core.converter.TelegramUpdateToMessageConverter;
import com.github.drednote.telegramstatemachine.core.monitor.TelegramStateMachineMonitor;
import com.github.drednote.telegramstatemachine.core.persist.TelegramStateMachinePersister;
import lombok.Getter;

@Getter
public class TelegramStateMachineConfigurerImpl<S> implements TelegramStateMachineConfigurer<S> {

  private TelegramStateMachineMonitor<S> monitor;
  private TelegramStateMachinePersister<S> persister;
  private TelegramUpdateToMessageConverter<S> converter;
  private ErrorTelegramHandler errorHandler;

  @Override
  public TelegramStateMachineConfigurer<S> withMonitor(TelegramStateMachineMonitor<S> monitor) {
    this.monitor = monitor;
    return this;
  }

  @Override
  public TelegramStateMachineConfigurer<S> withPersister(
      TelegramStateMachinePersister<S> persister) {
    this.persister = persister;
    return this;
  }

  @Override
  public TelegramStateMachineConfigurer<S> withConverter(
      TelegramUpdateToMessageConverter<S> converter) {
    this.converter = converter;
    return this;
  }

  @Override
  public TelegramStateMachineConfigurer<S> withErrorHandler(ErrorTelegramHandler handler) {
    this.errorHandler = handler;
    return this;
  }
}
