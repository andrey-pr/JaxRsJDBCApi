package org.dev.scud.orm;

import java.sql.*;

import org.dev.scud.configurations.JdbcConfigs;

public class DbConnectionDriver {
    private Connection conn;
    private Statement statement;

    void connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(
                JdbcConfigs.url, JdbcConfigs.username, JdbcConfigs.password);
    }

    ResultSet executeSqlQuery(String sql) throws SQLException {
        statement = conn.createStatement();
        return statement.executeQuery(sql);
    }

    int executeSql(String sql) throws SQLException {
        statement = conn.createStatement();
        return statement.executeUpdate(sql);
    }

    void closeStatement() throws SQLException {
        statement.close();
    }

    void disconnect() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
