package com.alejogalizzi.teams.security.config;

import com.alejogalizzi.teams.model.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON.getType());
    ObjectMapper mapper = new ObjectMapper();
    response.getWriter().write(mapper.writeValueAsString(getGenericErrorResponse()));
  }

  private Object getGenericErrorResponse() {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCodigo(HttpServletResponse.SC_UNAUTHORIZED);
    errorResponse.setMensaje("Acceso denegado. Por favor intente registrarse otra vez.");
    return errorResponse;
  }
}
