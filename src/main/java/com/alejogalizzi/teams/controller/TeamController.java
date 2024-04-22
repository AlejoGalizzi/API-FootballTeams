package com.alejogalizzi.teams.controller;

import com.alejogalizzi.teams.model.entity.Team;
import com.alejogalizzi.teams.model.response.ErrorResponse;
import com.alejogalizzi.teams.service.implementations.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Equipos", description = "API para gestionar equipos de fútbol")
@ApiResponses(value = {
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Acceso denegado",
        content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)))
})
public class TeamController {

  @Autowired
  private TeamService equipoService;

  @Operation(summary = "Consulta de Todos los Equipos")
  @GetMapping(produces = "application/json")
  @ApiResponses(value = {
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Equipos encontrados")
  })
  public ResponseEntity<List<Team>> getTeams() {
    return ResponseEntity.ok(equipoService.findAllTeams());
  }


  @Operation(summary = "Consulta de un Equipo por ID")
  @GetMapping(value = "/{id}",produces = "application/json")
  @ApiResponses(value = {
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Equipo encontrado"),
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Equipo no encontrado",
        content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
    return new ResponseEntity<>(equipoService.findTeamById(id), HttpStatus.OK);
  }

  @Operation(summary = "Búsqueda de Equipos por Nombre")
  @GetMapping(value = "/buscar", produces = "application/json")
  @ApiResponses(value = {
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Equipo encontrado"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Equipo no encontrado",
          content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<List<Team>> getTeamByName(@RequestParam("nombre") String name) {
    return new ResponseEntity<>(equipoService.findTeamByName(name), HttpStatus.OK);
  }

  @Operation(summary = "Creación de un Equipo")
  @PostMapping(produces = "application/json")
  @ApiResponses(value = {
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Equipo encontrado"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "La solicitud es invalida.",
          content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<Team> saveTeam(@RequestBody Team team) {
    return new ResponseEntity<>(equipoService.saveTeam(team), HttpStatus.CREATED);
  }

  @Operation(summary = "Actualización de Información de un Equipo")
  @ApiResponses(value = {
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Equipo encontrado"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Equipo no encontrado",
          content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class))),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "La solicitud es invalida.",
          content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)))
  })
  @PutMapping(value = "/{id}", produces = "application/json")
  public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team team) {
    return new ResponseEntity<>(equipoService.updateTeam(id, team), HttpStatus.OK);
  }

  @Operation(summary = "Eliminación de un Equipo")
  @DeleteMapping(value = "/{id}", produces = "application/json")
  @ApiResponses(value = {
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Equipo encontrado"),
      @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Equipo no encontrado",
          content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
    equipoService.deleteTeam(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
