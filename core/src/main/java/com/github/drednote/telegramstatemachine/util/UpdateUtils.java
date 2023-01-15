package com.github.drednote.telegramstatemachine.util;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.Update;

@UtilityClass
public class UpdateUtils {

  public String extractId(Update update) {
    return String.valueOf(update.getMessage().getFrom().getId());
  }
}
