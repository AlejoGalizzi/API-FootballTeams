package com.alejogalizzi.teams.util.seeder;

import com.alejogalizzi.teams.model.entity.User;
import com.alejogalizzi.teams.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder implements CommandLineRunner {

  private static final String USERNAME = "test";

  private static final String PASSWORD = "12345";

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    User user = new User();
    user.setUsername(USERNAME);
    user.setPassword(passwordEncoder.encode(PASSWORD));
    userRepository.save(user);
  }
}
