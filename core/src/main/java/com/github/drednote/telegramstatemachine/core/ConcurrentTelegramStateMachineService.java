package com.github.drednote.telegramstatemachine.core;

import com.github.drednote.telegramstatemachine.api.TelegramStateMachineAdapter;
import com.github.drednote.telegramstatemachine.exception.InternalTransitionException;
import com.github.drednote.telegramstatemachine.exception.TransitionException;
import com.github.drednote.telegramstatemachine.monitor.TelegramStateMachineMonitor;
import com.github.drednote.telegramstatemachine.persist.TelegramStateMachinePersister;
import com.github.drednote.telegramstatemachine.util.UpdateUtils;
import com.github.drednote.telegramstatemachine.util.lock.ReadWriteKeyLock;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class ConcurrentTelegramStateMachineService<S> extends
    DefaultTelegramStateMachineService<S> {

  private final ReadWriteKeyLock<String> lock = ReadWriteKeyLock.defaultInstance();
  private final long timeout;

  public ConcurrentTelegramStateMachineService(
      TelegramStateMachineAdapter<S> adapter, TelegramStateMachinePersister<S> persister,
      TelegramStateMachineMonitor<S> monitor, Long timeout) {
    super(adapter, persister, monitor);
    this.timeout = timeout == null ? 0L : timeout;
  }

  @Override
  public TelegramStateMachine<S> start(String id) throws TransitionException {
    try {
      lock.write().lock(id, timeout);
      return super.internalStart(id);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new InternalTransitionException("Cannot start telegram state machine", e);
    } catch (TimeoutException e) {
      throw new InternalTransitionException("Cannot start telegram state machine", e);
    } finally {
      lock.write().unlock(id);
    }
  }

  @Override
  public TelegramStateMachine<S> prepare(Update update) throws TransitionException {
    String id = UpdateUtils.extractId(update);
    try {
      lock.read().lock(id, timeout);
      return super.internalPrepare(update);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new InternalTransitionException("Cannot prepare telegram state machine", e);
    } catch (TimeoutException e) {
      throw new InternalTransitionException("Cannot prepare telegram state machine", e);
    } finally {
      lock.read().unlock(id);
    }
  }

  @Override
  public boolean transit(Update update) {
    String id = UpdateUtils.extractId(update);
    try {
      lock.write().lock(id, timeout);
      return super.internalTransit(update);
    } catch (TimeoutException e) {
      log.warn("Cannot change status cause: ", e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
      lock.write().unlock(id);
    }
    return false;
  }
}
