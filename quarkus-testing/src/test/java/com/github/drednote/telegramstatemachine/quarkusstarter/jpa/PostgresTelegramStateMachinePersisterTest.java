package com.github.drednote.telegramstatemachine.quarkusstarter.jpa;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachineService;
import com.github.drednote.telegramstatemachine.exception.transition.TransitionException;
import com.github.drednote.telegramstatemachine.quarkusstarter.jpa.mock.Mock;
import com.github.drednote.telegramstatemachine.quarkusstarter.jpa.config.PostgresTest;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;

@PostgresTest
class PostgresTelegramStateMachinePersisterTest {

  @Inject
  TelegramStateMachineService<String> service;

  @Test
  void name() throws TransitionException {
    String id = "1";
    Update update = Mock.createCommandUpdate(Long.valueOf(id));
    System.out.println("service = " + service);

    service.start(id);
    TelegramStateMachine<String> machine = service.start(id);
    assertThat(machine.getState()).isEqualTo("1");

    JpaTelegramStateMachine byId = JpaTelegramStateMachine.findById(id);
    System.out.println("byId = " + byId);

    boolean result = service.transit(update);
    assertThat(result).isTrue();
    machine = service.start(id);
    assertThat(machine.getState()).isEqualTo("2");
    TelegramStateMachine<String> start = service.start(id);
    assertThat(start.getState()).isEqualTo("2");

    assertThat(service.transit(update)).isTrue();
    assertThat(service.transit(update)).isTrue();
    machine = service.start(id);
    assertThat(machine.getState()).isEqualTo("3");
  }
}