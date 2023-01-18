package com.github.drednote.telegramstatemachine.exception.handler;

public abstract class HandlerException extends Exception {

  protected HandlerException(String message) {
    super(message);
  }

  protected HandlerException(String message, Throwable cause) {
    super(message, cause);
  }
}
