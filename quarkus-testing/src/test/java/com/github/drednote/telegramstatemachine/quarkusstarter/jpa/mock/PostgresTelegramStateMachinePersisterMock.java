package com.github.drednote.telegramstatemachine.quarkusstarter.jpa.mock;

import com.github.drednote.telegramstatemachine.quarkusstarter.jpa.PostgresTelegramStateMachinePersister;
import io.quarkus.test.Mock;

@Mock
public class PostgresTelegramStateMachinePersisterMock extends
    PostgresTelegramStateMachinePersister<String> {
}
