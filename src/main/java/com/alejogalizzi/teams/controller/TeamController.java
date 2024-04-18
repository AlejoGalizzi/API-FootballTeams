package com.alejogalizzi.teams.controller;

import com.alejogalizzi.teams.model.entity.Team;
import com.alejogalizzi.teams.service.implementations.TeamService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipos")
public class TeamController {

  @Autowired
  private TeamService equipoService;

  @GetMapping
  public ResponseEntity<List<Team>> getTeams() {
    return ResponseEntity.ok(equipoService.findAllTeams());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
    return new ResponseEntity<>(equipoService.findTeamById(id), HttpStatus.OK);
  }

  @GetMapping("/buscar")
  public ResponseEntity<List<Team>> getTeamByName(@RequestParam("nombre") String name) {
    return new ResponseEntity<>(equipoService.findTeamByName(name), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Team> saveTeam(@RequestBody Team team) {
    return new ResponseEntity<>(equipoService.saveTeam(team), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team team) {
    return new ResponseEntity<>(equipoService.updateTeam(id, team), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
    equipoService.deleteTeam(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
