package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.Data;

@Data
public class JdbcContext {

  private DataSource dataSource;

  public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
    try (
        Connection conn = dataSource.getConnection();
        PreparedStatement ps = stmt.makePreparedStatement(conn);
    ) {
      ps.executeUpdate();
    }
  }

  public void executeSql(final String query) throws SQLException {
    workWithStatementStrategy(c -> c.prepareStatement(query));
  }


}
