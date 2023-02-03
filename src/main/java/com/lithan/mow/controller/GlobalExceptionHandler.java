package com.lithan.mow.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lithan.mow.exception.UserNotActiveException;
import com.lithan.mow.payload.response.MessageResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(UserNotActiveException.class)
  public 
  @ResponseBody
  MessageResponse handleException(UserNotActiveException ex) {
    return new MessageResponse(ex.getMessage());
  }

  //todo: add other exception like file not found if needed
}
