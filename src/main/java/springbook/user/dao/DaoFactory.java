package springbook.user.dao;

import java.sql.DriverManager;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class DaoFactory {

  @Bean
  public UserDao userDao() {
    UserDao userDao = new UserDao();
    userDao.setDataSource(dataSource());

    return userDao;
  }

//  public AccountDao accountDao() {
//    ConnectionMaker connectionMaker = new AConnectionMaker();
//    return new AccountDao(connectionMaker());
//  }
//
//  public MessageDao messageDao() {
//    ConnectionMaker connectionMaker = new AConnectionMaker();
//    return new MessageDao(connectionMaker());
//  }

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
