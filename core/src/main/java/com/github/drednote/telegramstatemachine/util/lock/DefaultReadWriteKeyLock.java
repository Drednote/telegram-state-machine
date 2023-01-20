package com.github.drednote.telegramstatemachine.util.lock;

import com.github.drednote.telegramstatemachine.util.Assert;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public final class DefaultReadWriteKeyLock<K> implements ReadWriteKeyLock<K> {

  private final KeyLock<K> read;
  private final KeyLock<K> write;

  public DefaultReadWriteKeyLock() {
    Set<K> pool = new HashSet<>();
    this.write = new WriteKeyLock<>(pool);
    this.read = new ReadKeyLock<>(pool);
  }

  @Override
  public KeyLock<K> read() {
    return read;
  }

  @Override
  public KeyLock<K> write() {
    return write;
  }

  private record WriteKeyLock<K>(Set<K> pool) implements KeyLock<K> {

    @SuppressWarnings("Duplicates")
    public void lock(K id, long timeout) throws InterruptedException, TimeoutException {
      Assert.notNull(id, "'id' must not be null");
      synchronized (pool) {
        LocalDateTime dateTimeout = LocalDateTime.now().plus(timeout, ChronoUnit.MILLIS);
        while (pool.contains(id)) {
          pool.wait(timeout);
          if (timeout > 0L && LocalDateTime.now().isAfter(dateTimeout)) {
            throw new TimeoutException(String.format("Timeout while waiting to lock %s", id));
          }
        }
        pool.add(id);
      }
    }

    public void lock(K id) throws InterruptedException {
      try {
        this.lock(id, 0L);
      } catch (TimeoutException ignore) {
        // will not be
      }
    }

    public void unlock(K id) {
      Assert.notNull(id, "'id' must not be null");
      synchronized (pool) {
        pool.remove(id);
        pool.notifyAll();
      }
    }
  }

  private record ReadKeyLock<K>(Set<K> pool) implements KeyLock<K> {

    @SuppressWarnings("Duplicates")
    public void lock(K id, long timeout) throws InterruptedException, TimeoutException {
      Assert.notNull(id, "'id' must not be null");
      if (pool.contains(id)) {
        synchronized (pool) {
          LocalDateTime dateTimeout = LocalDateTime.now().plus(timeout, ChronoUnit.MILLIS);
          while (pool.contains(id)) {
            pool.wait(timeout);
            if (timeout > 0L && LocalDateTime.now().isAfter(dateTimeout)) {
              throw new TimeoutException(String.format("Timeout while waiting to lock %s", id));
            }
          }
        }
      }
    }

    public void lock(K id) throws InterruptedException {
      try {
        this.lock(id, 0L);
      } catch (TimeoutException ignore) {
        // will not be
      }
    }

    public void unlock(K id) {
      // nothing to do
    }
  }

}
