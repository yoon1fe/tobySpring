package springbook.user.dao;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import springbook.user.service.UserService;

@Configuration
public class DaoFactory {

  @Bean
  public UserDao userDao() {
    UserDaoJdbc userDao = new UserDaoJdbc();
    userDao.setDataSource(dataSource());

    return userDao;
  }

  @Bean
  public UserService userService(UserDao userDao) {
    UserService userService = new UserService();
    userService.setUserDao(userDao);

    return userService;

  }

  @Bean
  public JdbcContext jdbcContext() {
    JdbcContext jdbcContext = new JdbcContext();
    jdbcContext.setDataSource(dataSource());

    return jdbcContext;
  }

  @Bean
  public DataSource dataSource() {
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

    dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
    dataSource.setUrl("jdbc:mysql://0.0.0.0:9876/springbook");
    dataSource.setUsername("spring");
    dataSource.setPassword("book");

    return dataSource;
  }

}
