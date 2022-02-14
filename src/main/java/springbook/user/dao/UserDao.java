package springbook.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.User;

@Data
public class UserDao {

  private RowMapper<User> userRowMapper =
      new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
          return User.builder()
              .id(rs.getString("id"))
              .name(rs.getString("name"))
              .password(rs.getString("password"))
              .build();
        }
      };

  private JdbcTemplate jdbcTemplate;

  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public void add(User user) throws SQLException {

    jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)", user.getId(),
        user.getName(), user.getPassword());
  }


  public User get(String id) throws SQLException {
    return jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id}, this.userRowMapper);
  }

  public void deleteAll() throws SQLException {
    jdbcTemplate.update("delete from users");
  }

  public int getCount() throws SQLException {
    return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
  }

  public List<User> getAll() {
    return this.jdbcTemplate.query("select * from users order by id", this.userRowMapper);
  }
}
