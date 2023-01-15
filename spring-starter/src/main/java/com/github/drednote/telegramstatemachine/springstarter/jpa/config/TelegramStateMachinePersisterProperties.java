package com.github.drednote.telegramstatemachine.springstarter.jpa.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("drednote.telegramstatemachine.persister")
@Configuration
@Getter
@Setter
public class TelegramStateMachinePersisterProperties {

  /**
   * Persister type
   */
  private Type type = Type.IN_MEMORY;
}
