package com.github.drednote.telegramstatemachine.springstarter.jpa.serializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StringGenericSerializer<T> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  public String serialize(T t) {
    return t.toString();
  }

  public T deserialize(String str, TypeReference<T> typeReference) {
    return objectMapper.convertValue(str, typeReference);
  }
}
