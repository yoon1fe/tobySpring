package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import springbook.user.domain.User;

public class UserDao {

  public void add(User user) throws ClassNotFoundException, SQLException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection conn = DriverManager.getConnection("jdbc:mysql://0.0.0.0:9876/springbook", "spring",
        "book");

    PreparedStatement ps = conn.prepareStatement(
        "insert into users(id, name, password) values(?,?,?)");
    ps.setString(1, user.getId());
    ps.setString(2, user.getName());
    ps.setString(3, user.getPassword());

    ps.executeUpdate();

    ps.close();
    conn.close();
  }


  public User get(String id) throws ClassNotFoundException, SQLException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection conn = DriverManager.getConnection("jdbc:mysql://0.0.0.0:9876/springbook", "spring",
        "book");

    PreparedStatement ps = conn.prepareStatement("select * from users where id = ?");
    ps.setString(1, id);

    ResultSet rs = ps.executeQuery();
    rs.next();
    User user = User.builder()
        .id(rs.getString("id"))
        .name(rs.getString("name"))
        .password(rs.getString("password"))
        .build();

    rs.close();
    ps.close();
    conn.close();

    return user;
  }

  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    UserDao dao = new UserDao();

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
