package com.alejogalizzi.teams.service;

import java.util.List;
import com.alejogalizzi.teams.model.entity.Team;

public interface ITeamService {

  List<Team> findAllTeams();

  Team findTeamById(Long id);

  List<Team> findTeamByName(String name);

  Team saveTeam(Team team);

  Team updateTeam(long id, Team team);

  void deleteTeam(Long id);
}
