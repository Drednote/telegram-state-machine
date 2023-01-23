package com.github.drednote.telegramstatemachine.serializer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.drednote.telegramstatemachine.core.DefaultTelegramStateMachine;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;
import com.github.drednote.telegramstatemachine.core.kryo.TelegramStateMachineSerializationService;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class TelegramStateMachineContextSerializerTest {

  private final TelegramStateMachineSerializationService<State> serializer = new TelegramStateMachineSerializationService<>();

  @Test
  void name() throws IOException {
    byte[] serialize = serializer.serialize(
        new DefaultTelegramStateMachine<>("1", State.I, null, -1));
    TelegramStateMachine<State> deserialize = serializer.deserialize(serialize);
    assertThat(serialize).isNotEmpty();
    assertThat(deserialize.getState()).isEqualTo(State.I);
    assertThat(deserialize.getId()).isEqualTo("1");
    assertThat(deserialize.getStage()).isEqualTo(-1);
  }

  private enum State {
    I
  }
}
