package com.github.drednote.telegramstatemachine.message;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Message<S> {

  S state();

  String id();

  Update origin();

  /**
   * @return current stage, or -1 if transition is unstaged
   */
  int stage();
}
