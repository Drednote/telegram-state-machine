package com.github.drednote.telegramstatemachine;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.drednote.telegramstatemachine.config.Mock;
import com.github.drednote.telegramstatemachine.config.MultiStageTelegramStateMachineAdapter;
import com.github.drednote.telegramstatemachine.core.ConcurrentTelegramStateMachineService;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachineService;
import com.github.drednote.telegramstatemachine.exception.transition.TransitionException;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;

class MultiStageStateMachineTest {

  final TelegramStateMachineService<String> service = new ConcurrentTelegramStateMachineService<>(
      new MultiStageTelegramStateMachineAdapter(),
      TimeUnit.SECONDS.toMillis(2));

  @Test
  void name() throws TransitionException {
    service.start("1");
    Update update = Mock.createCommandUpdate(1L);
    assertThat(service.transit(update)).isTrue(); // 2_0
    assertThat(service.transit(update)).isTrue(); // 2_1
    assertThat(service.transit(update)).isTrue(); // 3
    var machine = service.start("1");
    assertThat(machine.getState()).isEqualTo("3");
  }
}
