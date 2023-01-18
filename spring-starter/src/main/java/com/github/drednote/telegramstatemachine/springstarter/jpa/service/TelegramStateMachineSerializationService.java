package com.github.drednote.telegramstatemachine.springstarter.jpa.service;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.github.drednote.telegramstatemachine.core.DefaultTelegramStateMachine;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;

public class TelegramStateMachineSerializationService<S> extends
    AbstractKryoSerializationService<S> {

  private final Serializer<TelegramStateMachine<S>> serializer = new TelegramStateMachineContextSerializer<>();

  @Override
  protected void doEncode(Kryo kryo, Object object, Output output) {
    kryo.writeObject(output, object, serializer);
  }

  @Override
  protected <T> T doDecode(Kryo kryo, Input input, Class<T> type) {
    return kryo.readObject(input, type);
  }

  @Override
  protected void configureKryoInstance(Kryo kryo) {
    kryo.register(TelegramStateMachine.class, serializer);
  }

  private static class TelegramStateMachineContextSerializer<S> extends
      Serializer<TelegramStateMachine<S>> {

    @Override
    public void write(Kryo kryo, Output output, TelegramStateMachine<S> object) {
      kryo.writeObject(output, object.getId());
      kryo.writeClassAndObject(output, object.getState());
    }

    @Override
    @SuppressWarnings("unchecked")
    public TelegramStateMachine<S> read(Kryo kryo, Input input,
        Class<TelegramStateMachine<S>> type) {
      String id = kryo.readObject(input, String.class);
      S state = ((S) kryo.readClassAndObject(input));
      return new DefaultTelegramStateMachine<>(id, state, null);
    }
  }
}
