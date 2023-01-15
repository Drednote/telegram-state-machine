package com.github.drednote.telegramstatemachine.util.lock;

public interface ReadWriteKeyLock<K> {

  KeyLock<K> read();

  KeyLock<K> write();

  static <K> ReadWriteKeyLock<K> defaultInstance() {
    return new DefaultReadWriteKeyLock<>();
  }
}
