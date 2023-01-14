package com.github.drednote.telegramstatemachine.core;

import com.github.drednote.telegramstatemachine.api.transition.Transition;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class DefaultTelegramStateMachine<S> implements TelegramStateMachine<S> {

  private final String id;
  private final S state;

  /**
   * can be empty if not prepared for transition
   */
  @ToString.Exclude
  @Nullable
  private final Transition<S> next;

  @Override
  public boolean isPrepared() {
    return next != null;
  }
}
