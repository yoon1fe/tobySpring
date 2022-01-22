package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.Setter;
import org.springframework.dao.EmptyResultDataAccessException;
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

    User user = null;
    if (rs.next()) {
      user = User.builder()
          .id(rs.getString("id"))
          .name(rs.getString("name"))
          .password(rs.getString("password"))
          .build();
    }

    rs.close();
    ps.close();
    conn.close();

    if (user == null) {
      throw new EmptyResultDataAccessException(1);
    }

    return user;
  }

  public void deleteAll() throws SQLException {
    Connection conn = dataSource.getConnection();

    PreparedStatement ps = conn.prepareStatement("delete from users");

    ps.executeUpdate();

    ps.close();
    conn.close();
  }

  public int getCount() throws SQLException {
    Connection conn = dataSource.getConnection();

    PreparedStatement ps = conn.prepareStatement("select count(*) from users");

    ResultSet rs = ps.executeQuery();

    rs.next();
    int count = rs.getInt(1);

    rs.close();
    ps.close();
    conn.close();

    return count;
  }

}
