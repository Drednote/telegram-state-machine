package com.github.drednote.telegramstatemachine.monitor;

public interface Transition<S> {

  S from();

  S to();

  String id();
}
