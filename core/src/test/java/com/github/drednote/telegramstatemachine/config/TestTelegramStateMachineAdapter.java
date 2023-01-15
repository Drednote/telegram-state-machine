package com.github.drednote.telegramstatemachine.config;

import com.github.drednote.telegramstatemachine.api.TelegramStateMachineAdapter;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachineConfigurer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestTelegramStateMachineAdapter implements TelegramStateMachineAdapter<String> {

  @Override
  public void onConfigure(TelegramStateMachineConfigurer<String> configurer) {
    configurer.withSimple()
        .source("1").target("2")
        .onUpdate(text -> log.info("1 command -> " + text))
        .matcher(update -> update.getMessage().isCommand())

        .and().withSimple()
        .source("2").target("3")
        .onUpdate(text -> log.info("2 command -> " + text))
        .matcher(update -> update.getMessage().isCommand())
        .and();
  }

  @Override
  public String initialState() {
    return "1";
  }
}
