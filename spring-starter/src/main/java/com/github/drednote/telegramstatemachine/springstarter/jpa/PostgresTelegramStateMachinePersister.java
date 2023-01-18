package com.github.drednote.telegramstatemachine.springstarter.jpa;

import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;
import com.github.drednote.telegramstatemachine.core.persist.TelegramStateMachinePersister;
import com.github.drednote.telegramstatemachine.springstarter.jpa.model.JpaTelegramStateMachine;
import com.github.drednote.telegramstatemachine.springstarter.jpa.repository.JpaTelegramStateMachineRepository;
import com.github.drednote.telegramstatemachine.springstarter.jpa.service.TelegramStateMachineSerializationService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class PostgresTelegramStateMachinePersister<S> implements TelegramStateMachinePersister<S> {

  private final JpaTelegramStateMachineRepository repository;
  private final TelegramStateMachineSerializationService<S> serializationService = new TelegramStateMachineSerializationService<>();

  @SneakyThrows
  @Override
  public TelegramStateMachine<S> persist(TelegramStateMachine<S> stateMachine) {
    JpaTelegramStateMachine jpaTelegramStateMachine = new JpaTelegramStateMachine();
    jpaTelegramStateMachine.setId(stateMachine.getId());
    jpaTelegramStateMachine.setState(stateMachine.getState().toString());
    jpaTelegramStateMachine.setContext(serializationService.serialize(stateMachine));
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
    return serializationService.deserialize(jpa.getContext());
  }
}
