package io.github.mhsscel.bookjavaapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedUserException extends RuntimeException
{
  public UnauthorizedUserException(String message) {
    super(message);
  }

  public UnauthorizedUserException(String message, Throwable cause) {
    super(message, cause);
  }
}
