package com.github.drednote.telegramstatemachine.springstarter.config;

import com.github.drednote.telegramstatemachine.api.TelegramStateMachineAdapter;
import com.github.drednote.telegramstatemachine.core.persist.TelegramStateMachinePersister;
import com.github.drednote.telegramstatemachine.springstarter.TelegramStateMachineAutoConfiguration;
import com.github.drednote.telegramstatemachine.springstarter.jpa.PostgresTelegramStateMachinePersister;
import com.github.drednote.telegramstatemachine.springstarter.jpa.repository.JpaTelegramStateMachineRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class Config extends TelegramStateMachineAutoConfiguration<String> {

  @Bean
  public TelegramStateMachinePersister<String> postgresTelegramStateMachinePersister(
      JpaTelegramStateMachineRepository repository) {
    return new PostgresTelegramStateMachinePersister<>(repository);
  }

  @Bean
  public TelegramStateMachineAdapter<String> telegramStateMachineAdapter(
      TelegramStateMachinePersister<String> persister) {
    return new TestTelegramStateMachineAdapter(persister);
  }
}
