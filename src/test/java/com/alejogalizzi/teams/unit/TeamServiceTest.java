package com.alejogalizzi.teams.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.alejogalizzi.teams.exception.InvalidRequestException;
import com.alejogalizzi.teams.exception.NotFoundException;
import com.alejogalizzi.teams.model.entity.Team;
import com.alejogalizzi.teams.repository.ITeamRepository;
import com.alejogalizzi.teams.service.ITeamService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TeamServiceTest extends BaseTestClass{

  @MockBean
  private ITeamRepository teamRepository;

  @Autowired
  private ITeamService teamService;

  @Override
  @BeforeEach
  public void setUp() {
    super.setUp();
  }

  @Test
  public void shouldFindAllTeams() {
    when(teamRepository.findAll()).thenReturn(mockTeams);

    List<Team> teamsResult = teamService.findAllTeams();

    assertEquals(2, teamsResult.size());
    assertEquals("Team A", teamsResult.get(0).getNombre());
    assertEquals("Team B", teamsResult.get(1).getNombre());
  }

  @Test
  public void shouldFindById() {
    when(teamRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(mockTeams.get(0)));

    Team teamsResult = teamService.findTeamById(1L);

    assertEquals("Team A", teamsResult.getNombre());
    assertEquals("League A", teamsResult.getLiga());
    assertEquals("Country A", teamsResult.getPais());
  }

  @Test
  public void shouldNotFindId() {
    when(teamRepository.findById(3L)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> {
      teamService.findTeamById(3L);
    });
  }

  @Test
  public void shouldFindByNameWithMultipleResults() {
    when(teamRepository.findByNombreContaining("Team")).thenReturn(mockTeams);

    List<Team> teamsResult = teamService.findTeamByName("Team");

    assertEquals(2, teamsResult.size());
  }

  @Test
  public void shouldFindByNameWithSingleResults() {
    when(teamRepository.findByNombreContaining("A")).thenReturn(Arrays.asList(mockTeams.get(0)));

    List<Team> teamsResult = teamService.findTeamByName("A");

    assertEquals(1, teamsResult.size());
    assertEquals("Team A", teamsResult.get(0).getNombre());
    assertEquals("League A", teamsResult.get(0).getLiga());
    assertEquals("Country A", teamsResult.get(0).getPais());
  }

  @Test
  public void shouldNotFindByName() {
    when(teamRepository.findByNombreContaining("TextoAleatorio")).thenReturn(new ArrayList<>());

    assertThrows(NotFoundException.class, () -> {
      teamService.findTeamByName("TextoAleatorio");
    });
  }

  @Test
  public void shouldSaveTeam() {
    when(teamRepository.save(any(Team.class))).thenReturn(new Team(1L, "Team C", "League C", "Country C"));

    Team teamResult = teamService.saveTeam(newTeam);

    assertEquals(1L, teamResult.getId());
    assertEquals("Team C", teamResult.getNombre());
    assertEquals("League C", teamResult.getLiga());
    assertEquals("Country C", teamResult.getPais());
  }

  @Test
  public void shouldNotSaveWithEmptyTeam() {
    when(teamRepository.save(any(Team.class))).thenThrow(new IllegalArgumentException());

    assertThrows(InvalidRequestException.class, () -> {
      teamService.saveTeam(new Team());
    });
  }

  @Test
  public void shouldUpdateTeam() {
    Team changeTeam = new Team(null, "Team D", "League D", "Country C");
    when(teamRepository.findById(1L)).thenReturn(Optional.ofNullable(newTeam));
    when(teamRepository.save(any(Team.class))).thenReturn(new Team(1L, "Team D", "League D", "Country C"));
    Team teamResult = teamService.updateTeam(1L, changeTeam);

    assertEquals(1L, teamResult.getId());
    assertEquals("Team D", teamResult.getNombre());
    assertEquals("League D", teamResult.getLiga());
    assertEquals("Country C", teamResult.getPais());
  }

  @Test
  public void shouldNotUpdateWithNoExistingTeam() {
    when(teamRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> {
      teamService.updateTeam(1L, any(Team.class));
    });
  }

}
