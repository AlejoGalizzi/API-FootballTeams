package com.alejogalizzi.teams.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.alejogalizzi.teams.model.response.ErrorResponse;

@ControllerAdvice
public class GlobalHandleException {

  @ExceptionHandler(value = NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleTeamNotFoundException(NotFoundException e) {
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

  @ExceptionHandler(value = AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
    ErrorResponse error = new ErrorResponse();
    error.setCodigo(401);
    error.setMensaje(e.getMessage());
    return new ResponseEntity<>(error, org.springframework.http.HttpStatus.UNAUTHORIZED);
  }
}
