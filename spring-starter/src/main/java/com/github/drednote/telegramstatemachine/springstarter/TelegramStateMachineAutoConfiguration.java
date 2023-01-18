package com.github.drednote.telegramstatemachine.springstarter;

import com.github.drednote.telegramstatemachine.api.TelegramStateMachineAdapter;
import com.github.drednote.telegramstatemachine.core.ConcurrentTelegramStateMachineService;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachineService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramStateMachineAutoConfiguration<S> {

  @Bean
  @ConditionalOnMissingBean(TelegramStateMachineService.class)
  @ConditionalOnBean(TelegramStateMachineAdapter.class)
  TelegramStateMachineService<S> telegramStateMachineService(
      TelegramStateMachineAdapter<S> adapter
  ) {
    return new ConcurrentTelegramStateMachineService<>(adapter, 5L);
  }
}
