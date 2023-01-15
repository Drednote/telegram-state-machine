package com.github.drednote.telegramstatemachine.monitor;


public record DefaultTransition<S>(String id, S from, S to) implements Transition<S> {
}
