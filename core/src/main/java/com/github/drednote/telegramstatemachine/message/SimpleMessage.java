package com.github.drednote.telegramstatemachine.message;

import java.util.Optional;
import javax.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.Update;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class SimpleMessage<S> implements Message<S> {

  private final String id;
  private final S state;
  private final int stage;
  @Nullable
  private final Update update;

  @Override
  public S state() {
    return state;
  }

  @Override
  public String id() {
    return id;
  }

  @Override
  public Optional<Update> origin() {
    return Optional.ofNullable(update);
  }

  @Override
  public int stage() {
    return stage;
  }
}
