package com.github.drednote.telegramstatemachine;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;

import com.github.drednote.telegramstatemachine.config.TestTelegramStateMachineAdapter;
import com.github.drednote.telegramstatemachine.config.TestTelegramStateMachineMonitor;
import com.github.drednote.telegramstatemachine.core.ConcurrentTelegramStateMachineService;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachineService;
import com.github.drednote.telegramstatemachine.exception.TransitionException;
import com.github.drednote.telegramstatemachine.monitor.DelegateTelegramStateMachineMonitor;
import com.github.drednote.telegramstatemachine.persist.InMemoryTelegramStateMachinePersister;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

class StateMachineTest {

  final TelegramStateMachineService<String> service = new ConcurrentTelegramStateMachineService<>(
      new TestTelegramStateMachineAdapter(),
      new InMemoryTelegramStateMachinePersister<>(),
      new DelegateTelegramStateMachineMonitor<>(new TestTelegramStateMachineMonitor<>()),
      TimeUnit.SECONDS.toMillis(2));

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

  @Test
  void concurrentTestOnUpdate()
      throws ExecutionException, InterruptedException, TransitionException {
    String id = "1";
    Update update = createUpdate(Long.valueOf(id));

    CompletableFuture<Void> first = CompletableFuture.runAsync(() -> {
      try {
        var start = service.start(id);
        System.out.printf("%s For thread %s machine started %s%n", LocalDateTime.now(),
            Thread.currentThread().getId(), start);
      } catch (TransitionException e) {
        fail("Something went wrong", e);
      }
      service.transit(update);
    });
    CompletableFuture<Void> second = CompletableFuture.runAsync(() -> {
      try {
        var start = service.start(id);
        System.out.printf("%s For thread %s machine started %s%n", LocalDateTime.now(),
            Thread.currentThread().getId(), start);
      } catch (TransitionException e) {
        fail("Something went wrong", e);
      }
      service.transit(update);
    });
    CompletableFuture.allOf(first, second).get();
    TelegramStateMachine<String> machine = service.start(id);
    assertThat(machine.getState()).isEqualTo("3");
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
