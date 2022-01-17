package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.Setter;
import springbook.user.domain.User;

@Setter
public class UserDao {

  private DataSource dataSource;


  public void add(User user) throws ClassNotFoundException, SQLException {
    Connection conn = dataSource.getConnection();

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
    Connection conn = dataSource.getConnection();

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

}
