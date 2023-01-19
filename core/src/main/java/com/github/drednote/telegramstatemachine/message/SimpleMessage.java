package com.github.drednote.telegramstatemachine.message;

import com.github.drednote.telegramstatemachine.util.UpdateUtils;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.Update;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class SimpleMessage<S> implements Message<S> {

  private final Update update;
  private final S state;
  private final int stage;

  @Override
  public S state() {
    return state;
  }

  @Override
  public String id() {
    return UpdateUtils.extractId(update);
  }

  @Override
  public Update origin() {
    return update;
  }

  @Override
  public int stage() {
    return stage;
  }
}
