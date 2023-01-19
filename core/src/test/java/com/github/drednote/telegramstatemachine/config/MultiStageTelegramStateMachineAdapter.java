package com.github.drednote.telegramstatemachine.config;

import com.github.drednote.telegramstatemachine.api.TelegramStateMachineAdapter;
import com.github.drednote.telegramstatemachine.core.transition.TelegramTransitionsStateMachineConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
public class MultiStageTelegramStateMachineAdapter implements TelegramStateMachineAdapter<String> {

  @Override
  public void onTransitions(TelegramTransitionsStateMachineConfigurer<String> configurer) {
    configurer.withSimple()
        .handler(Mock.emptyAndLog())
        .matcher(update -> update.getMessage().isCommand())
        .source("1").target("2")

        .and().withMultiStage()
        .handler(Mock.emptyAndLog())
        .matcher(update -> update.getMessage().isCommand())
        .source("2").target("3").count(2)

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
