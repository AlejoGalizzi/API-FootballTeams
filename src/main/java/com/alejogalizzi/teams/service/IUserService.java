package com.alejogalizzi.teams.service;

import com.alejogalizzi.teams.model.entity.User;

public interface IUserService {

  String login(User user);

  String register(User user);

}
