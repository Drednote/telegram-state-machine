package com.github.drednote.telegramstatemachine.springstarter.jpa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.drednote.telegramstatemachine.core.DefaultTelegramStateMachine;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;
import com.github.drednote.telegramstatemachine.persist.TelegramStateMachinePersister;
import com.github.drednote.telegramstatemachine.springstarter.jpa.model.JpaTelegramStateMachine;
import com.github.drednote.telegramstatemachine.springstarter.jpa.repository.TelegramStateMachineRepository;
import com.github.drednote.telegramstatemachine.springstarter.jpa.serializer.StringGenericSerializer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostgresTelegramStateMachinePersister<S> implements TelegramStateMachinePersister<S> {

  private final TelegramStateMachineRepository repository;
  private final StringGenericSerializer<S> serializer = new StringGenericSerializer<>();

  @Override
  public TelegramStateMachine<S> persist(TelegramStateMachine<S> stateMachine) {
    JpaTelegramStateMachine jpaTelegramStateMachine = new JpaTelegramStateMachine();
    jpaTelegramStateMachine.setId(stateMachine.getId());
    jpaTelegramStateMachine.setState(serializer.serialize(stateMachine.getState()));
    return build(repository.save(jpaTelegramStateMachine));
  }

  @Override
  public TelegramStateMachine<S> get(String id) {
    return repository.findById(id)
        .map(this::build)
        .orElseThrow(
            () -> new IllegalArgumentException("State machine with id %s not found".formatted(id)));
  }

  @Override
  public boolean contains(String id) {
    return repository.existsById(id);
  }

  private TelegramStateMachine<S> build(JpaTelegramStateMachine jpa) {
    S state = serializer.deserialize(jpa.getState(), new TypeReference<>() {});
    return new DefaultTelegramStateMachine<>(jpa.getId(), state, null);
  }
}
