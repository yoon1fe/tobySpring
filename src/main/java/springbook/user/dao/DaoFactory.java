package springbook.user.dao;

public class DaoFactory {

  public UserDao userDao() {
    ConnectionMaker connectionMaker = new AConnectionMaker();
    return new UserDao(connectionMaker());
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

  public ConnectionMaker connectionMaker() {
    return new AConnectionMaker();
  }

}
