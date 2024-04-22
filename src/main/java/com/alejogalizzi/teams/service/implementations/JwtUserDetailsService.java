package com.alejogalizzi.teams.service.implementations;

import com.alejogalizzi.teams.exception.NotFoundException;
import com.alejogalizzi.teams.model.entity.User;
import com.alejogalizzi.teams.repository.IUserRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private IUserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);

    if(user == null) {
      throw new NotFoundException("No se encontró el usuario con el nombre de usuario: " + username);
    }

    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
  }
}
