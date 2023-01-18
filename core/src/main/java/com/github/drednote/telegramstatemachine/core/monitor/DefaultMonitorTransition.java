package com.github.drednote.telegramstatemachine.core.monitor;


public record DefaultMonitorTransition<S>(String id, S from, S to) implements MonitorTransition<S> {
}
