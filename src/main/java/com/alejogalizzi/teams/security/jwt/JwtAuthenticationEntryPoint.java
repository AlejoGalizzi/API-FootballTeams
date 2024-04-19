package com.alejogalizzi.teams.security.jwt;

import com.alejogalizzi.teams.model.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON.getSubtype());
    ObjectMapper mapper = new ObjectMapper();
    response.getWriter().write(mapper.writeValueAsString(getGenericErrorResponse()));
  }

  private ErrorResponse getGenericErrorResponse() {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setCodigo(HttpServletResponse.SC_UNAUTHORIZED);
    errorResponse.setMensaje("Acceso denegado. Por favor intente registrarse otra vez.");
    return errorResponse;
  }
}
