package springbook.user.dao;

import javax.sql.DataSource;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import springbook.user.service.UserLevelUpgradePolicy;
import springbook.user.service.UserLevelUpgradePolicyImpl;
import springbook.user.service.UserService;
import springbook.user.service.UserServiceImpl;

@Configuration
public class DaoFactory {

  @Bean
  public UserDao userDao() {
    UserDaoJdbc userDao = new UserDaoJdbc();
    userDao.setDataSource(dataSource());

    return userDao;
  }

  @Bean
  public UserService userService(UserDao userDao, UserLevelUpgradePolicy policy) {
    UserServiceImpl userService = new UserServiceImpl();
    userService.setUserDao(userDao);
    userService.setUserLevelUpgradePolicy(policy);

    return userService;

  }

  @Bean
  public UserLevelUpgradePolicy userLevelUpgradePolicy() {
    return new UserLevelUpgradePolicyImpl();
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

  @Bean
  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    return new DefaultAdvisorAutoProxyCreator();
  }

}
