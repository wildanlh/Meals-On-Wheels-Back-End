package com.lithan.mow.exception;

public class UserNotActiveException extends RuntimeException {
  public UserNotActiveException(String message) {
      super(message);
  }

  public UserNotActiveException(String message, Throwable cause) {
      super(message, cause);
  }
}
