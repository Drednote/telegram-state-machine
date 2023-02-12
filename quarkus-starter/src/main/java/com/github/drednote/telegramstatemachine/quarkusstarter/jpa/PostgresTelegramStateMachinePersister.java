package com.github.drednote.telegramstatemachine.quarkusstarter.jpa;

import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;
import com.github.drednote.telegramstatemachine.core.kryo.TelegramStateMachineSerializationService;
import com.github.drednote.telegramstatemachine.core.persist.TelegramStateMachinePersister;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import lombok.SneakyThrows;

@Transactional(TxType.REQUIRED)
public class PostgresTelegramStateMachinePersister<S> implements TelegramStateMachinePersister<S> {

  private final TelegramStateMachineSerializationService<S> serializationService = new TelegramStateMachineSerializationService<>();

  @SneakyThrows
  @Override
  public TelegramStateMachine<S> persist(TelegramStateMachine<S> stateMachine) {
    JpaTelegramStateMachine jpaTelegramStateMachine = find(stateMachine.getId());

    jpaTelegramStateMachine.setState(stateMachine.getState().toString());
    jpaTelegramStateMachine.setContext(serializationService.serialize(stateMachine));

    jpaTelegramStateMachine.persist();

    return build(JpaTelegramStateMachine.findById(stateMachine.getId()));
  }

  @Override
  public TelegramStateMachine<S> get(String id) {
    return JpaTelegramStateMachine.findByIdOptional(id)
        .map(JpaTelegramStateMachine.class::cast)
        .map(this::build)
        .orElseThrow(
            () -> new IllegalArgumentException("State machine with id %s not found".formatted(id)));
  }

  @Override
  public boolean contains(String id) {
    return JpaTelegramStateMachine.findByIdOptional(id).isPresent();
  }

  private JpaTelegramStateMachine find(String id) {
    return JpaTelegramStateMachine.findByIdOptional(id)
        .map(JpaTelegramStateMachine.class::cast)
        .orElseGet(() -> new JpaTelegramStateMachine(id));
  }

  private TelegramStateMachine<S> build(JpaTelegramStateMachine jpa) {
    return serializationService.deserialize(jpa.getContext());
  }
}
