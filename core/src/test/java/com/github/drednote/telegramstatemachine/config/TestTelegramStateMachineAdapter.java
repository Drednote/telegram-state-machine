package com.github.drednote.telegramstatemachine.config;

import com.github.drednote.telegramstatemachine.api.TelegramStateMachineAdapter;
import com.github.drednote.telegramstatemachine.core.transition.TelegramTransitionsStateMachineConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
public class TestTelegramStateMachineAdapter implements TelegramStateMachineAdapter<String> {

  @Override
  public void onTransitions(TelegramTransitionsStateMachineConfigurer<String> configurer) {
    configurer.withSimple()
        .source("1").target("2")
        .handler(text -> {
          log.info("1 command -> " + text);
          return absSender -> {};
        })
        .matcher(update -> update.getMessage().isCommand())

        .and().withSimple()
        .source("2").target("3")
        .handler(text -> {
          log.info("2 command -> " + text);
          return absSender -> {};
        })
        .matcher(update -> update.getMessage().isCommand())
        .and();
  }

  @Override
  public String initialState() {
    return "1";
  }

  @Override
  public AbsSender absSender() {
    return new TestAbsSender();
  }
}
