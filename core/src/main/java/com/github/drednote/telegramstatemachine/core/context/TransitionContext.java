package com.github.drednote.telegramstatemachine.core.context;

import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;

public interface TransitionContext<S> {

  TelegramStateMachine<S> machine();

  /**
   * @return current stage, or -1 if transition is unstaged
   */
  int stage();
}
