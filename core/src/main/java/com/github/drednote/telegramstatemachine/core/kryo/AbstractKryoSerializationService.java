package com.github.drednote.telegramstatemachine.core.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoCallback;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.github.drednote.telegramstatemachine.core.TelegramStateMachine;
import com.github.drednote.telegramstatemachine.util.Assert;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractKryoSerializationService<S> {

  protected final KryoPool pool;

  protected AbstractKryoSerializationService() {
    KryoFactory factory = () -> {
      Kryo kryo = new Kryo();
      // kryo is really getting trouble checking things if class loaders
      // doesn't match. for now just use below trick before we try
      // to go fully on beans and get a bean class loader.
      kryo.setClassLoader(getDefaultClassLoader());
      configureKryoInstance(kryo);
      return kryo;
    };
    this.pool = new KryoPool.Builder(factory).softReferences().build();
  }

  public byte[] serialize(TelegramStateMachine<S> context) throws IOException {
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
      encode(context, bos);
      return bos.toByteArray();
    }
  }

  @SuppressWarnings("unchecked")
  public TelegramStateMachine<S> deserialize(byte[] bytes) {
    Assert.notNull(bytes, "'bytes' cannot be null");
    try (Input input = new Input(bytes)) {
      return decode(input, TelegramStateMachine.class);
    }
  }

  /**
   * Subclasses implement this method to encode with Kryo.
   *
   * @param kryo   the Kryo instance
   * @param object the object to encode
   * @param output the Kryo Output instance
   */
  protected abstract void doEncode(Kryo kryo, Object object, Output output);

  /**
   * Subclasses implement this method to decode with Kryo.
   *
   * @param kryo  the Kryo instance
   * @param input the Kryo Input instance
   * @param type  the class of the decoded object
   * @param <T>   the type for decoded object
   * @return the decoded object
   */
  protected abstract <T> T doDecode(Kryo kryo, Input input, Class<T> type);

  /**
   * Subclasses implement this to configure the kryo instance. This is invoked on each new Kryo
   * instance when it is created.
   *
   * @param kryo the kryo instance
   */
  protected abstract void configureKryoInstance(Kryo kryo);

  private void encode(final Object object, OutputStream outputStream) {
    Assert.notNull(object, "cannot encode a null object");
    Assert.notNull(outputStream, "'outputSteam' cannot be null");
    final Output output = (outputStream instanceof Output o
        ? o
        : new Output(outputStream));
    this.pool.run((KryoCallback<Void>) kryo -> {
      doEncode(kryo, object, output);
      return null;
    });
    output.close();
  }

  private <T> T decode(InputStream inputStream, final Class<T> type) {
    Assert.notNull(inputStream, "'inputStream' cannot be null");
    Assert.notNull(type, "'type' cannot be null");
    try (Input input = (inputStream instanceof Input i
        ? i
        : new Input(inputStream))) {
      return this.pool.run(kryo -> doDecode(kryo, input, type));
    }
  }

  private static ClassLoader getDefaultClassLoader() {
    ClassLoader cl = null;
    try {
      cl = Thread.currentThread().getContextClassLoader();
    } catch (Throwable ex) {
      // Cannot access thread context ClassLoader - falling back...
    }
    if (cl == null) {
      // No thread context class loader -> use class loader of this class.
      cl = AbstractKryoSerializationService.class.getClassLoader();
      if (cl == null) {
        // getClassLoader() returning null indicates the bootstrap ClassLoader
        try {
          cl = ClassLoader.getSystemClassLoader();
        } catch (Throwable ex) {
          // Cannot access system ClassLoader - oh well, maybe the caller can live with null...
        }
      }
    }
    return cl;
  }
}
