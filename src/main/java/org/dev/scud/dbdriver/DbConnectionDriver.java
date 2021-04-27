package org.dev.scud.dbdriver;

import java.sql.*;

import org.dev.scud.configurations.JdbcConfigs;

public class DbConnectionDriver {
    private Connection conn;
    private Statement statement;

    public void connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(
                JdbcConfigs.url, JdbcConfigs.username, JdbcConfigs.password);
    }

    public ResultSet executeSqlQuery(String sql) throws SQLException {
        statement = conn.createStatement();
        return statement.executeQuery(sql);
    }

    public int executeSql(String sql) throws SQLException {
        statement = conn.createStatement();
        return statement.executeUpdate(sql);
    }

    public void closeStatement() throws SQLException {
        statement.close();
    }

    public void disconnect() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
