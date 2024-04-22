package com.alejogalizzi.teams.unit;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alejogalizzi.teams.controller.TeamController;
import com.alejogalizzi.teams.exception.InvalidRequestException;
import com.alejogalizzi.teams.exception.NotFoundException;
import com.alejogalizzi.teams.model.entity.Team;
import com.alejogalizzi.teams.security.jwt.JwtTokenUtil;
import com.alejogalizzi.teams.service.implementations.JwtUserDetailsService;
import com.alejogalizzi.teams.service.implementations.TeamService;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(TeamController.class)
public class TeamControllerTest extends BaseTestClass {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private JwtUserDetailsService userDetailsService;

  @MockBean
  private JwtTokenUtil tokenUtil;

  @MockBean
  private TeamService teamService;

  @Override
  @BeforeEach
  public void setUp() {
    super.setUp();
    createTokenHeader();
  }

  @Test
  public void shouldThrowBadCredentialsError() throws Exception {
    when(tokenUtil.generateToken(any(UserDetails.class))).thenReturn("token");
    when(tokenUtil.validateToken(anyString(), anyString())).thenReturn(false);
    when(tokenUtil.getUsernameFromToken(anyString())).thenReturn(USERNAME);
    when(userDetailsService.loadUserByUsername(anyString())).thenReturn(new org.springframework.security.core.userdetails.User(USERNAME, PASSWORD, new ArrayList<>()));

    given(teamService.findAllTeams()).willReturn(mockTeams);

    mvc.perform(MockMvcRequestBuilders.get(TEAM_PATH).headers(headers)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void shouldFindAllTeams() throws Exception {
    when(tokenUtil.generateToken(any(UserDetails.class))).thenReturn("token");
    when(tokenUtil.validateToken(anyString(), anyString())).thenReturn(true);
    when(tokenUtil.getUsernameFromToken(anyString())).thenReturn(USERNAME);
    when(userDetailsService.loadUserByUsername(anyString())).thenReturn(new org.springframework.security.core.userdetails.User(USERNAME, PASSWORD, new ArrayList<>()));

    given(teamService.findAllTeams()).willReturn(mockTeams);

    mvc.perform(MockMvcRequestBuilders.get(TEAM_PATH).headers(headers)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].nombre").value(TEAM_NAME_1))
        .andExpect(jsonPath("$[1].nombre").value(TEAM_NAME_2));
  }

  @Test
  public void shouldFindTeamById() throws Exception {
    when(tokenUtil.generateToken(any(UserDetails.class))).thenReturn("token");
    when(tokenUtil.validateToken(anyString(), anyString())).thenReturn(true);
    when(tokenUtil.getUsernameFromToken(anyString())).thenReturn(USERNAME);
    when(userDetailsService.loadUserByUsername(anyString())).thenReturn(new org.springframework.security.core.userdetails.User(USERNAME, PASSWORD, new ArrayList<>()));

    given(teamService.findTeamById(1L)).willReturn(new Team(1L, TEAM_NAME_1, LEAGUE_NAME_1, COUNTRY_NAME_1));

    MvcResult result = mvc.perform(MockMvcRequestBuilders.get(TEAM_PATH+"/"+1L).headers(headers)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    Team responseTeam = mapFromJson(result.getResponse().getContentAsString(), Team.class);

    Assertions.assertEquals(Optional.of(1L), Optional.of(responseTeam.getId()));
    Assertions.assertEquals(TEAM_NAME_1, responseTeam.getNombre());
    Assertions.assertEquals(LEAGUE_NAME_1, responseTeam.getLiga());
    Assertions.assertEquals(COUNTRY_NAME_1, responseTeam.getPais());
  }

  @Test
  public void shouldNotFindTeamById() throws Exception {
    when(tokenUtil.generateToken(any(UserDetails.class))).thenReturn("token");
    when(tokenUtil.validateToken(anyString(), anyString())).thenReturn(true);
    when(tokenUtil.getUsernameFromToken(anyString())).thenReturn(USERNAME);
    when(userDetailsService.loadUserByUsername(anyString())).thenReturn(new org.springframework.security.core.userdetails.User(USERNAME, PASSWORD, new ArrayList<>()));

    given(teamService.findTeamById(anyLong())).willThrow(new NotFoundException("Equipo no encontrado"));

    mvc.perform(MockMvcRequestBuilders.get(TEAM_PATH+"/"+1L).headers(headers)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.mensaje").value("Equipo no encontrado"))
        .andExpect(jsonPath("$.codigo").value(404));

  }

  @Test
  public void shouldCreateTeam() throws Exception {
    when(tokenUtil.generateToken(any(UserDetails.class))).thenReturn("token");
    when(tokenUtil.validateToken(anyString(), anyString())).thenReturn(true);
    when(tokenUtil.getUsernameFromToken(anyString())).thenReturn(USERNAME);
    when(userDetailsService.loadUserByUsername(anyString())).thenReturn(new org.springframework.security.core.userdetails.User(USERNAME, PASSWORD, new ArrayList<>()));

    given(teamService.saveTeam(any(Team.class))).willReturn(new Team(1L, TEAM_NAME_1, LEAGUE_NAME_1, COUNTRY_NAME_1));

    MvcResult result = mvc.perform(MockMvcRequestBuilders.post(TEAM_PATH).headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(newTeam)))
        .andExpect(status().isCreated())
        .andReturn();

    Team responseTeam = mapFromJson(result.getResponse().getContentAsString(), Team.class);

    Assertions.assertEquals(Optional.of(1L), Optional.of(responseTeam.getId()));
    Assertions.assertEquals(TEAM_NAME_1, responseTeam.getNombre());
    Assertions.assertEquals(LEAGUE_NAME_1, responseTeam.getLiga());
    Assertions.assertEquals(COUNTRY_NAME_1, responseTeam.getPais());
  }

  @Test
  public void shouldNotCreateTeamWithEmptyField() throws Exception {
    when(tokenUtil.generateToken(any(UserDetails.class))).thenReturn("token");
    when(tokenUtil.validateToken(anyString(), anyString())).thenReturn(true);
    when(tokenUtil.getUsernameFromToken(anyString())).thenReturn(USERNAME);
    when(userDetailsService.loadUserByUsername(anyString())).thenReturn(new org.springframework.security.core.userdetails.User(USERNAME, PASSWORD, new ArrayList<>()));

    given(teamService.saveTeam(any(Team.class))).willThrow(new InvalidRequestException("La solicitud es invalida"));

    mvc.perform(MockMvcRequestBuilders.post(TEAM_PATH).headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(new Team(null, null, LEAGUE_NAME_1, COUNTRY_NAME_1))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.mensaje").value("La solicitud es invalida"))
        .andExpect(jsonPath("$.codigo").value(400));
  }

  @Test
  public void shouldUpdateTeam() throws Exception {
    when(tokenUtil.generateToken(any(UserDetails.class))).thenReturn("token");
    when(tokenUtil.validateToken(anyString(), anyString())).thenReturn(true);
    when(tokenUtil.getUsernameFromToken(anyString())).thenReturn(USERNAME);
    when(userDetailsService.loadUserByUsername(anyString())).thenReturn(new org.springframework.security.core.userdetails.User(USERNAME, PASSWORD, new ArrayList<>()));

    given(teamService.updateTeam(anyLong(), any(Team.class))).willReturn(new Team(1L, "Team C", "League C", "Country C"));

    MvcResult result = mvc.perform(MockMvcRequestBuilders.put(TEAM_PATH+"/"+1L).headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(newTeam)))
        .andExpect(status().isOk())
        .andReturn();

    Team responseTeam = mapFromJson(result.getResponse().getContentAsString(), Team.class);

    Assertions.assertEquals(Optional.of(1L), Optional.of(responseTeam.getId()));
    Assertions.assertEquals("Team C", responseTeam.getNombre());
    Assertions.assertEquals("League C", responseTeam.getLiga());
    Assertions.assertEquals("Country C", responseTeam.getPais());
  }

  @Test
  public void shouldNotUpdateTeamWithEmptyField() throws Exception {
    when(tokenUtil.generateToken(any(UserDetails.class))).thenReturn("token");
    when(tokenUtil.validateToken(anyString(), anyString())).thenReturn(true);
    when(tokenUtil.getUsernameFromToken(anyString())).thenReturn(USERNAME);
    when(userDetailsService.loadUserByUsername(anyString())).thenReturn(new org.springframework.security.core.userdetails.User(USERNAME, PASSWORD, new ArrayList<>()));

    given(teamService.updateTeam(anyLong(), any(Team.class))).willThrow(new InvalidRequestException("La solicitud es invalida"));

    mvc.perform(MockMvcRequestBuilders.put(TEAM_PATH+"/"+1L).headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(new Team(null, null, LEAGUE_NAME_1, COUNTRY_NAME_1))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.mensaje").value("La solicitud es invalida"))
        .andExpect(jsonPath("$.codigo").value(400));
  }

  @Test
  public void shouldDeleteTeam() throws Exception {
    when(tokenUtil.generateToken(any(UserDetails.class))).thenReturn("token");
    when(tokenUtil.validateToken(anyString(), anyString())).thenReturn(true);
    when(tokenUtil.getUsernameFromToken(anyString())).thenReturn(USERNAME);
    when(userDetailsService.loadUserByUsername(anyString())).thenReturn(new org.springframework.security.core.userdetails.User(USERNAME, PASSWORD, new ArrayList<>()));

    mvc.perform(MockMvcRequestBuilders.delete(TEAM_PATH+"/"+1L).headers(headers)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

}
