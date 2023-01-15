package com.github.drednote.telegramstatemachine.springstarter;

import com.github.drednote.telegramstatemachine.api.TelegramStateMachineAdapter;
import com.github.drednote.telegramstatemachine.core.ConcurrentTelegramStateMachineService;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachineConfigurer;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachineService;
import com.github.drednote.telegramstatemachine.monitor.DefaultTelegramStateMachineMonitor;
import com.github.drednote.telegramstatemachine.monitor.TelegramStateMachineMonitor;
import com.github.drednote.telegramstatemachine.persist.TelegramStateMachinePersister;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramStateMachineAutoConfiguration<S> {

  @Bean
  @ConditionalOnMissingBean(TelegramStateMachineService.class)
  TelegramStateMachineService<S> telegramStateMachine(
      TelegramStateMachineAdapter<S> adapter, TelegramStateMachinePersister<S> persister,
      TelegramStateMachineMonitor<S> monitor
  ) {
    return new ConcurrentTelegramStateMachineService<>(adapter, persister, monitor, 5L);
  }

  @Bean
  @ConditionalOnMissingBean(TelegramStateMachineAdapter.class)
  TelegramStateMachineAdapter<S> telegramStateMachineAdapter() {
    return new TelegramStateMachineAdapter<>() {
      @Override
      public void onConfigure(TelegramStateMachineConfigurer<S> configurer) {
        // nothing to do
      }

      @Override
      public S initialState() {
        return null;
      }
    };
  }

  @Bean
  @ConditionalOnMissingBean(TelegramStateMachineMonitor.class)
  TelegramStateMachineMonitor<S> telegramStateMachineMonitor() {
    return new DefaultTelegramStateMachineMonitor<>();
  }
}
