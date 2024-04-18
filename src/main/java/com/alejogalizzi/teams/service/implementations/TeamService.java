package com.alejogalizzi.teams.service.implementations;

import com.alejogalizzi.teams.exception.InvalidRequestException;
import com.alejogalizzi.teams.exception.TeamNotFoundException;
import com.alejogalizzi.teams.model.entity.Team;
import com.alejogalizzi.teams.repository.ITeamRepository;
import com.alejogalizzi.teams.service.ITeamService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService implements ITeamService {

  @Autowired
  private ITeamRepository teamRepository;

  @Override
  public List<Team> findAllTeams() {
    return teamRepository.findAll();
  }

  @Override
  public Team findTeamById(Long id) {
    Team team = teamRepository.findById(id).orElse(null);
    if(team == null) {
      throw new TeamNotFoundException("Equipo no encontrado");
    }

    return team;
  }

  @Override
  public List<Team> findTeamByName(String name) {
    List<Team> teams = teamRepository.findByNombreContaining(name);
    if(teams.isEmpty()) {
      throw new TeamNotFoundException("No se encontro ningun equipo que contenga ese nombre");
    }

    return teams;
  }

  @Override
  public Team saveTeam(Team team) {
    try {
      Team newTeam = new Team();
      newTeam.setNombre(team.getNombre());
      newTeam.setLiga(team.getLiga());
      newTeam.setPais(team.getPais());
      return teamRepository.save(newTeam);
    }catch (Exception e) {
      throw new InvalidRequestException("La solicitud es invalida");
    }
  }

  @Override
  public Team updateTeam(long id, Team team) {
    Team dbTeam = teamRepository.findById(id).orElse(null);
    if(dbTeam == null) {
      throw new TeamNotFoundException("Equipo no encontrado");
    }
    try {
      dbTeam.setNombre(team.getNombre());
      dbTeam.setLiga(team.getLiga());
      dbTeam.setPais(team.getPais());
      return teamRepository.save(dbTeam);
    }catch (Exception e) {
      throw new InvalidRequestException("La solicitud es invalida");
    }
  }

  @Override
  public void deleteTeam(Long id) {
    Team team = teamRepository.findById(id).orElse(null);
    if(team == null) {
      throw new TeamNotFoundException("Equipo no encontrado");
    }
    teamRepository.delete(team);
  }
}
