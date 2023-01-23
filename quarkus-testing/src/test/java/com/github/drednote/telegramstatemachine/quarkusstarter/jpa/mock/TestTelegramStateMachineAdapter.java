package com.github.drednote.telegramstatemachine.quarkusstarter.jpa.mock;

import com.github.drednote.telegramstatemachine.api.TelegramStateMachineAdapter;
import com.github.drednote.telegramstatemachine.core.configurer.TelegramStateMachineConfigurer;
import com.github.drednote.telegramstatemachine.core.persist.TelegramStateMachinePersister;
import com.github.drednote.telegramstatemachine.core.transition.TelegramTransitionsStateMachineConfigurer;
import jakarta.enterprise.inject.Produces;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@io.quarkus.test.Mock
@RequiredArgsConstructor
public class TestTelegramStateMachineAdapter implements TelegramStateMachineAdapter<String> {

  private final TelegramStateMachinePersister<String> persister;

  @Override
  public void onConfigure(TelegramStateMachineConfigurer<String> configurer) {
    configurer.withPersister(persister);
  }

  @Override
  public void onTransitions(TelegramTransitionsStateMachineConfigurer<String> configurer) {
    configurer.withSimple()
        .source("1").target("2")
        .handler(Mock.emptyAndLog())
        .matcher(update -> update.getMessage().isCommand())

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
    return new TelegramLongPollingBot() {
      @Override
      public void onUpdateReceived(Update update) {

      }

      @Override
      public String getBotUsername() {
        return null;
      }

      @Override
      public String getBotToken() {
        return null;
      }
    };
  }
}
