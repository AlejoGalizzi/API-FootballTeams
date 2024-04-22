package com.alejogalizzi.teams.unit;

import com.alejogalizzi.teams.model.entity.Team;
import com.alejogalizzi.teams.model.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.web.client.TestRestTemplate;
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

  protected final List<Team> mockTeams = new ArrayList<>();

  protected final Team newTeam = new Team();

  protected final User newUser = new User();

  protected void setUp() {
    mockTeams.addAll(Arrays.asList(new Team(1L, TEAM_NAME_1, LEAGUE_NAME_1, COUNTRY_NAME_1),
        new Team(2L, TEAM_NAME_2, LEAGUE_NAME_2, COUNTRY_NAME_2)));
    newTeam.setNombre(TEAM_NAME_1);
    newTeam.setLiga(LEAGUE_NAME_1);
    newTeam.setPais(COUNTRY_NAME_1);
    newUser.setUsername(USERNAME);
    newUser.setPassword(PASSWORD);
    newUser.setId(1L);
  }

  protected HttpHeaders headers = new HttpHeaders();

  protected void createTokenHeader() {
    Map<String, Object> claims = new HashMap<>();
    headers.set(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", createTokenInHeader(claims)));
  }

  protected String createURLWithPort(String uri) {
    return "http://localhost:8080" + uri;
  }

  protected String mapToJson(Object obj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(obj);
  }
  protected <T> T mapFromJson(String json, Class<T> clazz)
      throws JsonParseException, JsonMappingException, IOException {

    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(json, clazz);
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
