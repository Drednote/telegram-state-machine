package com.github.drednote.telegramstatemachine.springstarter.jpa.config;

import com.github.drednote.telegramstatemachine.persist.InMemoryTelegramStateMachinePersister;
import com.github.drednote.telegramstatemachine.persist.TelegramStateMachinePersister;
import com.github.drednote.telegramstatemachine.springstarter.jpa.PostgresTelegramStateMachinePersister;
import com.github.drednote.telegramstatemachine.springstarter.jpa.repository.TelegramStateMachineRepository;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableConfigurationProperties(TelegramStateMachinePersisterProperties.class)
@AutoConfigureAfter(JpaBaseConfiguration.class)
@EnableJpaRepositories(basePackageClasses = TelegramStateMachineRepository.class)
public class TelegramStateMachinePersisterAutoConfiguration {

  @Configuration
  static class Default<S> {

    @Bean
    @ConditionalOnMissingBean(TelegramStateMachinePersister.class)
    @ConditionalOnProperty(
        prefix = "drednote.telegramstatemachine.persister",
        value = "type",
        havingValue = "IN_MEMORY",
        matchIfMissing = true
    )
    TelegramStateMachinePersister<S> telegramStateMachinePersisterInMemory() {
      return new InMemoryTelegramStateMachinePersister<>();
    }
  }

  @Configuration
  @ConditionalOnClass(JpaRepository.class)
  static class Postgres<S> {

    @Bean
    @ConditionalOnMissingBean(TelegramStateMachinePersister.class)
    @ConditionalOnProperty(
        prefix = "drednote.telegramstatemachine.persister",
        value = "type",
        havingValue = "POSTGRES"
    )
    TelegramStateMachinePersister<S> telegramStateMachinePersisterPostgres(
        TelegramStateMachineRepository repository) {
      return new PostgresTelegramStateMachinePersister<>(repository);
    }
  }
}
