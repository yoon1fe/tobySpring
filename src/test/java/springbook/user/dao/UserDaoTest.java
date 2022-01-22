package springbook.user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;

public class UserDaoTest {

  private UserDao dao;
  private User user1;
  private User user2;
  private User user3;

  @Before
  public void setUp() {
    ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
    this.dao = context.getBean("userDao", UserDao.class);

    this.user1 = User.builder().id("yoon1fe").name("yoon1fe").password("1234").build();
    this.user2 = User.builder().id("yoon2fe").name("yoon2fe").password("1234").build();
    this.user3 = User.builder().id("yoon3fe").name("yoon3fe").password("1234").build();
  }

  @Test
  public void addAndGet() throws SQLException, ClassNotFoundException {
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
  public void count() throws SQLException, ClassNotFoundException {
    dao.deleteAll();
    assertThat(dao.getCount(), is(0));

    for (int i = 1; i <= 3; i++) {
      User user = User.builder()
          .id("yoon1fe" + i)
          .name("yoon1fe")
          .password("1234")
          .build();

      dao.add(user);
      assertThat(dao.getCount(), is(i));
    }
  }

  @Test(expected = EmptyResultDataAccessException.class)
  public void getUserFailure() throws SQLException, ClassNotFoundException {
    dao.deleteAll();
    assertThat(dao.getCount(), is(0));

    dao.get("unkown");
  }
}
