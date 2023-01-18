package com.github.drednote.telegramstatemachine.springstarter.config;

import com.github.drednote.telegramstatemachine.springstarter.jpa.repository.JpaTelegramStateMachineRepository;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public interface TelegramStateMachineRepository extends JpaTelegramStateMachineRepository {
}
