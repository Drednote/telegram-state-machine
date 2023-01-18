package com.github.drednote.telegramstatemachine.core.monitor;

public interface MonitorTransition<S> {

  S from();

  S to();

  String id();
}
