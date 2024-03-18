package io.nology.todolistspringbackend.exceptions;

import org.springframework.http.HttpStatus;

public class ErrorMessage {

  private String message;
  private HttpStatus statusCode;

  public ErrorMessage(String message, HttpStatus statusCode) {
    this.message = message;
    this.statusCode = statusCode;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatus getStatusCode() {
    return statusCode;
  }
}
