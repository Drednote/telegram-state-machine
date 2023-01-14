package com.github.drednote.telegramstatemachine.api;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateTelegramHandler {

  void onUpdate(Update update);
}
