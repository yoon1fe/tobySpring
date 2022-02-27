package springbook.user.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import springbook.user.domain.User;

@Getter
@Setter
public class UserServiceTx implements UserService {
  UserService userService;
  PlatformTransactionManager transactionManager;

  @Override
  public void upgradeLevels() {
    userService.upgradeLevels();
  }

  @Override
  public void add(User user) {
    TransactionStatus status = this.transactionManager.getTransaction(new DefaultTransactionDefinition());
    try {
      userService.upgradeLevels();
      this.transactionManager.commit(status);
    } catch (RuntimeException e) {
      this.transactionManager.rollback(status);
      throw e;
    }
  }
}
