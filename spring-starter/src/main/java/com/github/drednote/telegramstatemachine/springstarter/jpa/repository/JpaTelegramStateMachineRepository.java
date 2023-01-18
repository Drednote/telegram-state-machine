package com.github.drednote.telegramstatemachine.springstarter.jpa.repository;

import com.github.drednote.telegramstatemachine.springstarter.jpa.model.JpaTelegramStateMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaTelegramStateMachineRepository extends
    JpaRepository<JpaTelegramStateMachine, String> {
}
