package com.github.drednote.telegramstatemachine.springstarter.jpa.repository;

import com.github.drednote.telegramstatemachine.springstarter.jpa.model.JpaTelegramStateMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramStateMachineRepository extends
    JpaRepository<JpaTelegramStateMachine, String> {
}
