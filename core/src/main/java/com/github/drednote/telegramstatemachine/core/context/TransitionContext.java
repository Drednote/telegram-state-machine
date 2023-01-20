package com.github.drednote.telegramstatemachine.core.context;

import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;

/**
 * Контекст необходимый для конвертера, чтобы преобразовать update в Message
 *
 * @param <S>
 * @see com.github.drednote.telegramstatemachine.core.converter.TelegramUpdateToMessageConverter
 */
public interface TransitionContext<S> {

  TelegramStateMachine<S> machine();

  /**
   * @return current stage, or -1 if transition is unstaged
   */
  int stage();
}
