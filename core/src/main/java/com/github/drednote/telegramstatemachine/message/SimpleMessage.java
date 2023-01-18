package com.github.drednote.telegramstatemachine.message;

import com.github.drednote.telegramstatemachine.util.UpdateUtils;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class SimpleMessage<S> implements Message<S> {

  private final Update update;
  private final S state;

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
}
