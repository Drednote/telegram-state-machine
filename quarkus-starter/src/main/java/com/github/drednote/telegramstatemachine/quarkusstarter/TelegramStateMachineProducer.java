package com.github.drednote.telegramstatemachine.quarkusstarter;

import com.github.drednote.telegramstatemachine.api.TelegramStateMachineAdapter;
import com.github.drednote.telegramstatemachine.core.ConcurrentTelegramStateMachineService;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachineService;
import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import org.telegram.telegrambots.meta.bots.AbsSender;

@ApplicationScoped
public class TelegramStateMachineProducer {

  @DefaultBean
  @Produces
  @Singleton
  <S> TelegramStateMachineService<S> telegramStateMachineService(
      TelegramStateMachineAdapter<S> adapter
  ) {
    return new ConcurrentTelegramStateMachineService<>(adapter, 5L);
  }

  @DefaultBean
  @Produces
  @Singleton
  <S> TelegramStateMachineAdapter<S> telegramStateMachineAdapter() {
    return new TelegramStateMachineAdapter<>() {
      @Override
      public S initialState() {
        return null;
      }

      @Override
      public AbsSender absSender() {
        return null;
      }
    };
  }
}
