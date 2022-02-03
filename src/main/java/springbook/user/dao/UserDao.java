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

  public void add(User user) throws SQLException {

    try (
        Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement("insert into users(id, name, password) values(?,?,?)");
    ) {
      ps.setString(1, user.getId());
      ps.setString(2, user.getName());
      ps.setString(3, user.getPassword());

      ps.executeUpdate();
    }

  }


  public User get(String id) throws SQLException {
    try (
        Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement("select * from users where id = ?");
    ) {
      ps.setString(1, id);
      try (
          ResultSet rs = ps.executeQuery();
      ) {
        User user = null;
        if (rs.next()) {
          user = User.builder()
              .id(rs.getString("id"))
              .name(rs.getString("name"))
              .password(rs.getString("password"))
              .build();
        }

        if (user == null) {
          throw new EmptyResultDataAccessException(1);
        }

        return user;
      }
    }
  }

  public void deleteAll() throws SQLException {
    StatementStrategy strategy = new DeleteAllStatement();
    jdbcContextWithStatementStrategy(strategy);
  }

  public int getCount() throws SQLException {
    try (
        Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement("select count(*) from users");
        ResultSet rs = ps.executeQuery()
    ) {
      rs.next();

      return rs.getInt(1);
    }
  }

  public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
    try (
        Connection conn = dataSource.getConnection();
        PreparedStatement ps = stmt.makePreparedStatement(conn);
    ) {
      ps.executeUpdate();
    }
  }

}
