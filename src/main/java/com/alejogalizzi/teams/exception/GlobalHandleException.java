package com.alejogalizzi.teams.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.alejogalizzi.teams.model.response.ErrorResponse;

@ControllerAdvice
public class GlobalHandleException {

  @ExceptionHandler(value = TeamNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleTeamNotFoundException(TeamNotFoundException e) {
    ErrorResponse error = new ErrorResponse();
    error.setCodigo(404);
    error.setMensaje(e.getMessage());
    return new ResponseEntity<>(error, org.springframework.http.HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = InvalidRequestException.class)
  public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException e) {
    ErrorResponse error = new ErrorResponse();
    error.setCodigo(400);
    error.setMensaje(e.getMessage());
    return new ResponseEntity<>(error, org.springframework.http.HttpStatus.BAD_REQUEST);
  }
}
