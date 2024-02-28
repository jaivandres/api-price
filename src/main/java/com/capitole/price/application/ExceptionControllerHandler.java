/* (C) Copyright 2024 Capitole. */
package com.capitole.price.application;

import static org.springframework.http.ResponseEntity.status;

import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.capitole.price.exception.ApiErrorDTO;
import com.capitole.price.exception.BusinessException;
import com.capitole.price.utils.JsonUtils;

import lombok.extern.log4j.Log4j2;

/** Handler that captures the exceptions and selects the correct exception and code to be sent to
 * the one that requested the endpoint. */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Log4j2
public class ExceptionControllerHandler extends ResponseEntityExceptionHandler {

  private static Map<String, Object> getParams(HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    return Map.of("request", request.toString(), "headers", StringUtils.join(headers), "status",
        status.toString());
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    // Logging the given info
    var params = getParams(headers, status, request);
    log.info("handleMissingPathVariable",
        "Error in the path variables: %s".formatted(ex.getMessage()), params);
    return status(HttpStatus.BAD_REQUEST)
        .body(JsonUtils.jsonResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    // Logging the given info
    var params = getParams(headers, status, request);
    log.info("handleMissingServletRequestParameter",
        "Error in the request parameters: %s".formatted(ex.getMessage()), params);
    return status(HttpStatus.BAD_REQUEST)
        .body(JsonUtils.jsonResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    // Logging the given info
    var params = getParams(headers, status, request);
    log.info("handleHttpMessageNotReadable",
        "The request cannot be interpreted: %s".formatted(ex.getMessage()), params);
    return status(HttpStatus.BAD_REQUEST)
        .body(JsonUtils.jsonResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    // Logging the given info
    var params = getParams(headers, status, request);
    log.info("handleMethodArgumentNotValid",
        "The given arguments cannot be processed: %s".formatted(ex.getMessage()), params);
    return status(HttpStatus.BAD_REQUEST)
        .body(JsonUtils.jsonResponse(HttpStatus.BAD_REQUEST,
            Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage()));
  }

  /** {@inheritDoc} */
  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    // Logging the given info
    var params = getParams(headers, status, request);
    log.info("handleHttpRequestMethodNotSupported",
        "The REST method is not allowed: %s".formatted(ex.getMessage()), params);
    return status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(JsonUtils.jsonResponse(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage()));
  }

  /** Handler for a request with invalid data exception.
   *
   * @param ex The exception that caused the error
   * @return The HTTP response with the status and error message. */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Object> invalidRequestHandler(IllegalArgumentException ex) {
    log.info("invalidRequestHandler",
        "Error on illegal argument detected: %s".formatted(ex.getMessage()));

    return status(HttpStatus.BAD_REQUEST)
        .body(JsonUtils.jsonResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<Object> businessExceptionHandler(BusinessException ex) {
    var errorCode = ex.getErrorCode();
    var businessExceptionDTO = ApiErrorDTO.apply(errorCode);
    log.info("businessExceptionHandler",
        "businessExceptionDTO: %s".formatted(businessExceptionDTO));
    return status(errorCode.httpStatus).body(businessExceptionDTO);
  }

  /** Handler for a unexpected exception.
   *
   * @param ex The exception that caused the error.
   * @return The HTTP response with the status and error message. */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> unexpectedExceptionHandler(Exception ex) {
    // Logging the given info
    log.error("unexpectedExceptionHandler",
        "Error on unexpected exception detected: %s".formatted(ex.getMessage()), ex);

    return status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(JsonUtils.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected Error"));
  }

  @ExceptionHandler(value = {MissingRequestHeaderException.class})
  protected ResponseEntity<Object> handleConflict(MissingRequestHeaderException ex,
      WebRequest request) {
    log.info("MissingRequestHeaderException",
        "MissingRequestHeaderException: %s".formatted(ex.getMessage()));
    return status(HttpStatus.BAD_REQUEST)
        .body(JsonUtils.jsonResponse(HttpStatus.BAD_REQUEST, ex.getHeaderName() + " is required"));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    log.info("MethodArgumentTypeMismatchException",
        "MethodArgumentTypeMismatchException: %s".formatted(ex.getMessage()));
    String message = String.format("%s should be a valid %s and %s is not",
        ex.getName(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName(), ex.getValue());
    return status(HttpStatus.BAD_REQUEST)
        .body(JsonUtils.jsonResponse(HttpStatus.BAD_REQUEST, message));
  }

}
