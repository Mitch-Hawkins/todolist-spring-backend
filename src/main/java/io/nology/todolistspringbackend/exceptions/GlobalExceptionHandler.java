package io.nology.todolistspringbackend.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Object> handleResourceNotFoundException(
    ResourceNotFoundException ex
  ) {
    ErrorMessage errorMessage = new ErrorMessage(
      ex.getMessage(),
      HttpStatus.NOT_FOUND
    );
    return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<Object> handleValidationException(
    ValidationException ex
  ) {
    ErrorMessage errorMessage = new ErrorMessage(
      ex.getMessage(),
      HttpStatus.BAD_REQUEST
    );
    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationException(
    MethodArgumentNotValidException ex
  ) {
    Map<String, String> errors = new HashMap<>();
    ex
      .getBindingResult()
      .getAllErrors()
      .forEach(error -> {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
      });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  // Add more exception handlers for other custom exceptions

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGenericException(Exception ex) {
    ErrorMessage errorMessage = new ErrorMessage(
      "Internal server error",
      HttpStatus.INTERNAL_SERVER_ERROR
    );
    return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
