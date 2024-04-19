package com.alejogalizzi.teams.service.implementations;

import com.alejogalizzi.teams.exception.AuthenticationException;
import com.alejogalizzi.teams.model.entity.User;
import com.alejogalizzi.teams.model.response.JwtResponse;
import com.alejogalizzi.teams.repository.IUserRepository;
import com.alejogalizzi.teams.security.jwt.JwtTokenUtil;
import com.alejogalizzi.teams.service.IUserService;
import com.alejogalizzi.teams.security.jwt.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private JwtUserDetailsService userDetailsService;

  @Override
  public String login(User user) {
    if(userRepository.findByUsername(user.getUsername()) == null) {
      throw new AuthenticationException("No existe un usuario con ese nombre de usuario");
    }
    final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
    return jwtTokenUtil.generateToken(userDetails);
  }

  @Override
  public String register(User user) {
    if(userRepository.findByUsername(user.getUsername()) != null) {
      throw new AuthenticationException("Ya existe un usuario con ese nombre de usuario");
    }
    User dbUser = new User();
    dbUser.setUsername(user.getUsername());
    dbUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    userRepository.save(dbUser);
    authenticate(dbUser.getUsername(), dbUser.getPassword());
    final UserDetails userDetails = userDetailsService.loadUserByUsername(dbUser.getUsername());

    return jwtTokenUtil.generateToken(userDetails);
  }

  private void authenticate(String username, String password) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new AuthenticationException("Este usuario esta deshabilitado.");
    } catch (BadCredentialsException e) {
      throw new AuthenticationException("Credenciales invalidas.");
    }
  }

}
