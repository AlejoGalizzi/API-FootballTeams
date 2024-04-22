package com.alejogalizzi.teams.controller;

import com.alejogalizzi.teams.model.entity.User;
import com.alejogalizzi.teams.model.response.JwtResponse;
import com.alejogalizzi.teams.service.IUserService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequestMapping("/auth")
public class UserController {

  @Autowired
  private IUserService userService;

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@RequestBody User user) {
    return new ResponseEntity<>(new JwtResponse(userService.login(user)), HttpStatus.OK);
  }

  @PostMapping("/register")
  public ResponseEntity<JwtResponse> register(@RequestBody User user) {
    return new ResponseEntity<>(new JwtResponse(userService.register(user)), HttpStatus.CREATED);
  }
}
