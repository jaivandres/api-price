/* (C) Copyright 2024 Capitole. */
package com.capitole.price.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/** Utility class to get things from JSON Nodes. */
public class JsonUtils {

  /** The unique instance to use when mapping objects. */
  public static final ObjectMapper instance = new ObjectMapper();

  /** The format to transform dates with time */
  public static final DateTimeFormatter DATETIME_FORMAT =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  /** The format to transform dates */
  public static final DateTimeFormatter DATE_FORMAT =
      DateTimeFormatter.ofPattern("yyyy-MM-dd");

  // Registering all the special JSON modules defined in the POM or the
  // code to add more context to certain serialization and deserialization
  // of specific types like dates.
  static {
    instance.findAndRegisterModules();
  }

  /** Private constructor to force the use of the instance. */
  private JsonUtils() {}

  /** Gets the value from a json node as a string.
   *
   * @param node Json node from where to extract info, cannot be null.
   * @param fieldName Name of the field from whom to extract the value, cannot be null or blank, if
   *        the field comes in camel case and the node returns null then tries to get the same field
   *        in camel case.
   * @return The value, or null if the value is not present. */
  public static String getStringFromNode(JsonNode node, String fieldName) {
    String response = null;
    if (node.hasNonNull(fieldName)) {
      response = node.get(fieldName).asText();
    }
    return response;
  }

  /** Gets the value from a json node as a date.
   *
   * @param node Json node from where to extract info, cannot be null.
   * @param fieldName Name of the field from whom to extract the value, cannot be null or blank, if
   *        the field comes in camel case and the node returns null then tries to get the same field
   *        in camel case.
   * @return The value, or null if the value is not present. */
  public static LocalDate getDateFromNode(JsonNode node, String fieldName) {
    String result = getStringFromNode(node, fieldName);
    if (result != null && !result.isBlank()) {
      return LocalDate.parse(result, DATE_FORMAT);
    }
    return null;
  }

  /** Gets the value from a json node as a date time.
   *
   * @param node Json node from where to extract info, cannot be null.
   * @param fieldName Name of the field from whom to extract the value, cannot be null or blank, if
   *        the field comes in camel case and the node returns null then tries to get the same field
   *        in camel case.
   * @return The value, or null if the value is not present. */
  public static LocalDateTime getDateTimeFromNode(
      JsonNode node, String fieldName) {
    String result = getStringFromNode(node, fieldName);
    if (result != null && !result.isBlank()) {
      if (result.contains(".")) {
        result = result.split("\\.")[0];
      }
      return LocalDateTime.parse(result, DATETIME_FORMAT);
    }
    return null;
  }

  /** Gets the value from a json node as a string, if the value does not exists returns the given
   * default value.
   *
   * @param node Json node from where to extract info, cannot be null.
   * @param fieldName Name of the field from whom to extract the value, cannot be null or blank, if
   *        the field comes in camel case and the node returns null then tries to get the same field
   *        in camel case.
   * @param defaultValue The default value to take if the node returns null or blank.
   * @return The value, or null if teh value is not present. */
  public static String getStringFromNodeOrDefault(
      JsonNode node, String fieldName, String defaultValue) {
    String response = getStringFromNode(node, fieldName);
    return response == null || response.isBlank() ? defaultValue : response;
  }

  /** Creates the response.
   *
   * @param status the http status to expose.
   * @param message the message to display.
   * @return the json, never null. */
  public static ObjectNode jsonResponse(
      final HttpStatus status, final String message) {
    ObjectNode json = JsonUtils.instance.createObjectNode();
    json.put("message", message);
    json.put("code", status.value());
    return json;
  }

  /** Creates the response.
   *
   * @param code the http status to expose.
   * @param message the message to display.
   * @param data the message to display.
   * @return the json, never null. */
  public static ObjectNode jsonResponse(String code,
      String message,
      Object data) {
    ObjectNode json = JsonUtils.instance.createObjectNode();
    json.put("code", code);
    json.put("message", message);
    json.set("data", instance.valueToTree(data));
    return json;
  }

}
