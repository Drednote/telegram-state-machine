package com.github.drednote.telegramstatemachine.quarkusstarter.jpa.mock;

import com.github.drednote.telegramstatemachine.api.TelegramStateMachineAdapter;
import com.github.drednote.telegramstatemachine.core.ConcurrentTelegramStateMachineService;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachineService;
import com.github.drednote.telegramstatemachine.quarkusstarter.TelegramStateMachineProducer;
import com.github.drednote.telegramstatemachine.quarkusstarter.jpa.PostgresTelegramStateMachinePersister;
import io.quarkus.test.Mock;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Mock
public class TelegramStateMachineProducerMock extends TelegramStateMachineProducer {

  @Produces
  @Singleton
  TelegramStateMachineService<String> telegramStateMachineService(
      TelegramStateMachineAdapter<String> adapter
  ) {
    return new ConcurrentTelegramStateMachineService<>(adapter, 5L);
  }
}
