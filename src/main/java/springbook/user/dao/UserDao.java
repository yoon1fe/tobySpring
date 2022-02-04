package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.Data;
import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;

@Data
public class UserDao {

  private DataSource dataSource;
  private JdbcContext jdbcContext;

  public void add(User user) throws SQLException {

    StatementStrategy st = c -> {
      PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");

      ps.setString(1, user.getId());
      ps.setString(2, user.getName());
      ps.setString(3, user.getPassword());

      return ps;
    };

    jdbcContext.workWithStatementStrategy(st);
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
    StatementStrategy strategy = c -> c.prepareStatement("delete from users");
    jdbcContext.workWithStatementStrategy(strategy);
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

}
