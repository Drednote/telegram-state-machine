package com.github.drednote.telegramstatemachine.springstarter.jpa;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachineService;
import com.github.drednote.telegramstatemachine.exception.transition.TransitionException;
import com.github.drednote.telegramstatemachine.springstarter.config.Config;
import com.github.drednote.telegramstatemachine.springstarter.config.SqlTestContainerBase;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Import({Config.class})
class PostgresTelegramStateMachinePersisterTest extends SqlTestContainerBase {

  @Autowired
  TelegramStateMachineService<String> service;

  @Test
  void testOnUpdate() throws TransitionException {
    String id = "1";
    Update update = createUpdate(Long.valueOf(id));

    service.start(id);
    TelegramStateMachine<String> machine = service.start(id);
    assertThat(machine.getState()).isEqualTo("1");
    boolean result = service.transit(update);
    assertThat(result).isTrue();
    machine = service.start(id);
    assertThat(machine.getState()).isEqualTo("2");
    TelegramStateMachine<String> start = service.start(id);
    assertThat(start.getState()).isEqualTo("2");
  }

  private Update createUpdate(Long id) {
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
}