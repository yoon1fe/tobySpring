package springbook.user.dao;

import java.sql.SQLException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.domain.User;

public class UserDaoTest {

  public static void main(String[] args) throws SQLException, ClassNotFoundException {

    ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

    UserDao dao = context.getBean("userDao", UserDao.class);

    User user = User.builder()
        .id("yoon1fe")
        .name("yoon1fe")
        .password("1234")
        .build();

    dao.add(user);

    System.out.println(user.getId() + " 등록 성공!");

    User user2 = dao.get(user.getId());
    System.out.println(user2.getName());
    System.out.println(user2.getPassword());

    System.out.println(user2.getId() + " 조회 성공!");
  }
}
