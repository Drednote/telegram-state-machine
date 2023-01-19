package com.github.drednote.telegramstatemachine.core.context;

import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;

public record TransitionContextImpl<S>(
    TelegramStateMachine<S> machine, int stage
) implements TransitionContext<S> {}
