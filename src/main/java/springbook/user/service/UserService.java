package springbook.user.service;

import springbook.user.domain.User;

public interface UserService {

  void upgradeLevels();

  void add(User user);
}
