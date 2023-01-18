package com.github.drednote.telegramstatemachine.config;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TestAbsSender extends TelegramLongPollingBot {

  @Override
  public String getBotUsername() {
    return null;
  }

  @Override
  public String getBotToken() {
    return null;
  }

  @Override
  public void onUpdateReceived(Update update) {

  }
}
