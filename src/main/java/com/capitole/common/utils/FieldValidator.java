/* (C) Copyright 2024 Frubana. */
package com.capitole.price.common.utils;

import java.util.Objects;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.util.Strings;

public class FieldValidator {

  public static void nonBlank(String val, String fieldName) {
    nonNull(val, fieldName);
    if (Strings.isBlank(val)) {
      throw new IllegalArgumentException(String.format("%s cannot be blank", fieldName));
    }
  }

  public static void nonNull(Object val, String fieldName) {
    if (Objects.isNull(val)) {
      throw new IllegalArgumentException(String.format("%s cannot be null", fieldName));
    }
  }

  public static void positive(Number val, String fieldName) {
    nonNull(val, fieldName);
    if (val.intValue() < 0) {
      throw new IllegalArgumentException(String.format("%s cannot be negative", fieldName));
    }
  }

  public static void isParsable(String val, String fieldName) {
    nonNull(val, fieldName);
    if (!NumberUtils.isParsable(val)) {
      throw new IllegalArgumentException(String.format("%s is not be parsable", fieldName));
    }
  }
}
