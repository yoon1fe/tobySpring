package springbook.user.service;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

@Setter
@Getter
public class UserService {

  public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
  public static final int MIN_RECOMMEND_FOR_GOLD = 30;
  UserDao userDao;
  UserLevelUpgradePolicy userLevelUpgradePolicy;
  int recommend;

  public void upgradeLevels() {
    List<User> users = userDao.getAll();

    for (User user : users) {
      if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
        userLevelUpgradePolicy.upgradeLevel(user);
        userDao.update(user);
      }
    }
  }

  public void add(User user) {
    if (user.getLevel() == null) user.setLevel(Level.BASIC);

    userDao.add(user);
  }
}
