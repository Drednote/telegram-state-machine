package com.github.drednote.telegramstatemachine.api.transition;

import com.github.drednote.telegramstatemachine.api.UpdateTelegramHandler;
import com.github.drednote.telegramstatemachine.matcher.UpdateMatcher;

public record Transition<S>(
    S source,
    S target,
    UpdateTelegramHandler handler,
    UpdateMatcher matcher
) {}
