package com.github.drednote.telegramstatemachine.message;

import java.util.Optional;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Message<S> {

  S state();

  String id();

  Optional<Update> origin();

  /**
   * @return current stage, or -1 if transition is unstaged
   */
  int stage();
}
