package com.esgworks.errors;

import com.esgworks.exceptions.DuplicateException;
import com.esgworks.exceptions.InvalidRequestException;

import com.esgworks.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleAuthFail(AuthenticationException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
      .body(new ErrorResponse("UNAUTHORIZED", ex.getMessage()));
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
      .body(new ErrorResponse("NOT_FOUND", ex.getMessage()));
  }

  @ExceptionHandler(DuplicateException.class)
  public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
      .body(new ErrorResponse("DUPLICATE", ex.getMessage()));
  }

  @ExceptionHandler(InvalidRequestException.class)
  public ResponseEntity<ErrorResponse> handleInvalidRequest(InvalidRequestException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(new ErrorResponse("VALIDATION_ERROR", ex.getMessage(), ex.getInvalidFields()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(new ErrorResponse("INTERNAL_ERROR", "예기치 않은 오류가 발생했습니다."));
  }
}
