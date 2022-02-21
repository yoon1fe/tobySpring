package springbook.user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.domain.Level;
import springbook.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactory.class)
public class UserDaoTest {

  @Autowired
  private UserDao dao;
  private User user1;
  private User user2;
  private User user3;

  @Before
  public void setUp() {
    this.user1 = User.builder().id("yoon1fe").name("yoon1fe").password("1234").level(Level.BASIC).login(1).recommend(0).build();
    this.user2 = User.builder().id("yoon2fe").name("yoon2fe").password("1234").level(Level.SILVER).login(55).recommend(10).build();
    this.user3 = User.builder().id("yoon3fe").name("yoon3fe").password("1234").level(Level.GOLD).login(100).recommend(40).build();
  }

  @Test
  public void addAndGet() {
    dao.deleteAll();
    assertThat(dao.getCount(), is(0));

    dao.add(user1);
    assertThat(dao.getCount(), is(1));

    System.out.println(user1.getId() + " 등록 성공!");

    User user2 = dao.get(user1.getId());

    assertThat(user2.getName(), is(user1.getName()));
    assertThat(user2.getPassword(), is(user1.getPassword()));
  }

  @Test
  public void count() {
    dao.deleteAll();
    assertThat(dao.getCount(), is(0));

    for (int i = 1; i <= 3; i++) {
      User user = User.builder()
          .id("yoon1fe" + i)
          .name("yoon1fe")
          .password("1234")
          .level(Level.valueOf(i))
          .login(0)
          .recommend(1)
          .build();

      dao.add(user);
      assertThat(dao.getCount(), is(i));
    }
  }

  @Test(expected = EmptyResultDataAccessException.class)
  public void getUserFailure() {
    dao.deleteAll();
    assertThat(dao.getCount(), is(0));

    dao.get("unkown");
  }

  @Test
  public void getAll() {
    dao.deleteAll();

    List<User> users0 = dao.getAll();
    assertThat(users0.size(), is(0));

    dao.add(user1);
    dao.add(user2);
    dao.add(user3);

    List<User> users = dao.getAll();
    assertThat(users.size(), is(3));
    checkSameUser(user1, users.get(0));
    checkSameUser(user2, users.get(1));
    checkSameUser(user3, users.get(2));
  }

  @Test
  public void update() {
    dao.deleteAll();

    dao.add(user1);

    user1.setName("wowow");
    user1.setPassword("123456");
    user1.setLevel(Level.GOLD);
    user1.setLogin(1000);
    user1.setRecommend(999);
    int result = dao.update(user1);

    User user1update = dao.get(user1.getId());
    checkSameUser(user1, user1update);
    assertThat(result, is(1));
  }

  private void checkSameUser(User user1, User user2) {
    assertThat(user1.getId(), is(user2.getId()));
    assertThat(user1.getName(), is(user2.getName()));
    assertThat(user1.getPassword(), is(user2.getPassword()));
    assertThat(user1.getLevel(), is(user2.getLevel()));
    assertThat(user1.getLogin(), is(user2.getLogin()));
    assertThat(user1.getRecommend(), is(user2.getRecommend()));
  }

}
