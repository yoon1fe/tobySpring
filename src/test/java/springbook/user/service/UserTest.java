package springbook.user.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UserTest {

  User user;

  @Before
  public void setUp() {
    user = new User();
  }

  @Test()
  public void upgradeLevel() {
    Level[] levels = Level.values();

    for (Level level : levels) {
      if (level.nextLevel() == null) continue;

      user.setLevel(level);
      user.upgradeLevel();
      assertThat(user.getLevel(), is(level.nextLevel()));
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void cannotUpgradeLevel() {
    Level[] levels = Level.values();

    for (Level level : levels) {
      if (level.nextLevel() != null) continue;

      user.setLevel(level);
      user.upgradeLevel();
    }
  }

}
