package com.github.drednote.telegramstatemachine.core;

import com.github.drednote.telegramstatemachine.core.transition.Transition;

public interface TelegramStateMachine<S> {

  String getId();

  S getState();

  Transition<S> getNext();

  boolean isPrepared();

  int getStage();
}
