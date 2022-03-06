package springbook.user.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static springbook.user.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static springbook.user.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.dao.DaoFactory;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactory.class)
public class UserServiceTest {

  @Autowired
  UserService userService;
  @Autowired
  UserDao userDao;

  List<User> users;

  @Test
  public void bean() {
    assertThat(this.userService, is(notNullValue()));
  }

  @Before
  public void setUp() {
    users = Arrays.asList(
        new User("aaa", "aaa", "123", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0),
        new User("bbb", "bbb", "123", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
        new User("ccc", "ccc", "123", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD - 1),
        new User("ddd", "ddd", "123", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
        new User("eee", "eee", "123", Level.GOLD, 100, Integer.MAX_VALUE)
        );
  }

  @Test
  public void upgradeLevels() throws Exception {
    UserServiceImpl userServiceImpl = new UserServiceImpl();

    UserLevelUpgradePolicy userLevelUpgradePolicy = new UserLevelUpgradePolicyImpl();
    UserDao mockUserDao = mock(UserDao.class);
    when(mockUserDao.getAll()).thenReturn(this.users);

    userServiceImpl.setUserDao(mockUserDao);
    userServiceImpl.setUserLevelUpgradePolicy(userLevelUpgradePolicy);

    userServiceImpl.upgradeLevels();

    verify(mockUserDao, times(2)).update(any((User.class)));
    verify(mockUserDao).update(users.get(1));
    assertThat(users.get(1).getLevel(), is(Level.SILVER));
    verify(mockUserDao).update(users.get(3));
    assertThat(users.get(3).getLevel(), is(Level.GOLD));
  }

  private void checkLevelUpgraded(User user, boolean upgraded) {
    User userUpdate = userDao.get(user.getId());
    if (upgraded) {
      assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
    } else {
      assertThat(userUpdate.getLevel(), is(user.getLevel()));
    }
  }

  private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
    assertThat(updated.getId(), is(expectedId));
    assertThat(updated.getLevel(), is(expectedLevel));
  }

  @Test
  public void add() {
    userDao.deleteAll();

    User userWithLevel = users.get(4);
    User userWithoutLevel = users.get(0);
    userWithoutLevel.setLevel(null);

    userService.add(userWithLevel);
    userService.add(userWithoutLevel);

    User userWithLevelRead = userDao.get(userWithLevel.getId());
    User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

    assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
    assertThat(userWithoutLevelRead.getLevel(), is(userWithoutLevel.getLevel()));
  }

  @Getter
  static class MockUserDao implements UserDao {

    private List<User> users;
    private List<User> updated = new ArrayList<>();

    private MockUserDao(List<User> users) {
      this.users = users;
    }

    @Override
    public void add(User user) {
      throw new UnsupportedOperationException();
    }

    @Override
    public User get(String id) {
      throw new UnsupportedOperationException();
    }

    @Override
    public List<User> getAll() {
      return this.users;
    }

    @Override
    public void deleteAll() {
      throw new UnsupportedOperationException();
    }

    @Override
    public int getCount() {
      throw new UnsupportedOperationException();
    }

    @Override
    public int update(User user) {
      updated.add(user);
      return 1;
    }
  }

}
