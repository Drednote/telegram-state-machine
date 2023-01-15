package com.github.drednote.telegramstatemachine.core;

import com.github.drednote.telegramstatemachine.api.transition.Transition;

public interface TelegramStateMachine<S> {

  String getId();

  S getState();

  Transition<S> getNext();

  boolean isPrepared();
}
