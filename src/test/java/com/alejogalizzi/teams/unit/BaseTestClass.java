package com.alejogalizzi.teams.unit;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.client.MockRestServiceServer;

public class BaseTestClass {

  @Value("jwt.secret")
  private String secret;

  protected static final String USERNAME = "test";

  protected static final String PASSWORD = "passwordTest";

  protected static final String TEAM_PATH = "/equipos";

  protected static final String USER_PATH = "/auth";

  protected static final String LOGIN_PATH = USER_PATH + "/login";

  protected static final String REGISTER_PATH = USER_PATH + "/register";

  protected static final String TEAM_NAME_1 = "Team A";

  protected static final String TEAM_NAME_2 = "Team B";

  protected static final String LEAGUE_NAME_1 = "League A";

  protected static final String LEAGUE_NAME_2 = "League B";

  protected static final String COUNTRY_NAME_1 = "Country A";

  protected static final String COUNTRY_NAME_2 = "Country B";

  protected TestRestTemplate restTemplate = new TestRestTemplate();

  protected MockRestServiceServer mockServer;
  protected HttpHeaders headers = new HttpHeaders();

  protected void createTokenHeader() {
    Map<String, Object> claims = new HashMap<>();
    headers.set(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", createTokenInHeader(claims)));
  }

  protected String createURLWithPort(String uri) {
    return "http://localhost:8080" + uri;
  }

  private String createTokenInHeader(Map<String, Object> claims) {

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(USERNAME)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)).signWith(
            SignatureAlgorithm.HS256, secret).compact();
  }
}
