package com.github.drednote.telegramstatemachine.config;

import com.github.drednote.telegramstatemachine.api.UpdateTelegramHandler;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Slf4j
public class Mock {

  public static Update createCommandUpdate(Long id) {
    String text = "/start";

    User from = new User();
    from.setId(id);

    Message message = new Message();
    message.setText(text);
    message.setFrom(from);
    message.setEntities(List.of(
        MessageEntity.builder()
            .text(text)
            .type("bot_command")
            .offset(0)
            .length(text.length())
            .build()
    ));

    Update update = new Update();
    update.setMessage(message);
    return update;
  }

  public static <S> UpdateTelegramHandler<S> emptyAndLog() {
    return message -> {
      log.info("Message: id = {}, state = {}, stage = {}", message.id(), message.state(),
          message.stage());
      return absSender -> {
      };
    };
  }
}
