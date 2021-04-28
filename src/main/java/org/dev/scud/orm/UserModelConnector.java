package org.dev.scud.orm;

import java.sql.*;
import java.util.UUID;

import org.dev.scud.dbdriver.DbConnectionDriver;
import org.dev.scud.models.User;
import org.dev.scud.orm.interfaces.UserModelConnectorInterface;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Default
@ApplicationScoped
public class UserModelConnector implements UserModelConnectorInterface {
    DbConnectionDriver driver;

    public UserModelConnector() throws SQLException, ClassNotFoundException {
        driver = new DbConnectionDriver();
        driver.connect();
    }

    @Override
    public User[] getAllUsers() throws SQLException {
        ResultSet rs = driver.executeSqlQuery("SELECT COUNT(*) AS total FROM users;");
        rs.next();
        User[] users = new User[rs.getInt("total")];
        rs = driver.executeSqlQuery("SELECT * FROM users;");
        for (int i = 0; i < users.length; i++) {
            rs.next();
            users[i] = new User(rs.getString("id"), rs.getString("name"));
        }
        driver.closeStatement();
        return users;
    }

    @Override
    public User getUser(String id) throws SQLException, IllegalAccessException {
        if (isSqlInjection(id))
            throw new IllegalAccessException("SQL injection detected");
        UUID.fromString(id);
        ResultSet rs = driver.executeSqlQuery("SELECT * FROM users WHERE id='" + id + "';");
        if (!rs.next())
            return null;
        User user = new User(rs.getString("id"), rs.getString("name"));
        driver.closeStatement();
        return user;
    }

    @Override
    public User getUser(UUID id) throws SQLException, IllegalAccessException {
        return getUser(id.toString());
    }

    @Override
    public boolean createUser(String id, String name) throws SQLException, IllegalAccessException {
        if (isSqlInjection(id) || isSqlInjection(name))
            throw new IllegalAccessException("SQL injection detected");
        UUID.fromString(id);
        try {
            driver.executeSql(
                    "INSERT INTO users (`id`, `name`) VALUES ('"
                            + id + "', '" + name + "');");
            driver.closeStatement();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            driver.closeStatement();
            return false;
        }
    }

    @Override
    public boolean createUser(UUID id, String name) throws SQLException, IllegalAccessException {
        return createUser(id.toString(), name);
    }

    @Override
    public boolean createUser(User user) throws SQLException, IllegalAccessException {
        return createUser(user.id, user.name);
    }

    @Override
    public boolean updateUser(String id, String name) throws SQLException, IllegalAccessException {
        if (isSqlInjection(id) || isSqlInjection(name))
            throw new IllegalAccessException("SQL injection detected");
        UUID.fromString(id);
        int res = driver.executeSql(
                "UPDATE users SET `name` = '" + name + "' WHERE (`id` = '" + id + "');");
        driver.closeStatement();
        return res > 0;
    }

    @Override
    public boolean updateUser(UUID id, String name) throws SQLException, IllegalAccessException {
        return updateUser(id.toString(), name);
    }

    @Override
    public boolean updateUser(User user) throws SQLException, IllegalAccessException {
        return updateUser(user.id.toString(), user.name);
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, IllegalAccessException {
        if (isSqlInjection(id))
            throw new IllegalAccessException("SQL injection detected");
        UUID.fromString(id);
        int res = driver.executeSql("DELETE FROM users WHERE id = '" + id + "';");
        driver.closeStatement();
        return res > 0;
    }

    @Override
    public boolean deleteUser(UUID id) throws SQLException, IllegalAccessException {
        return deleteUser(id.toString());
    }

    @Override
    public boolean deleteUser(User user) throws SQLException, IllegalAccessException {
        return deleteUser(user.id.toString());
    }

    @Override
    public void disconnect() throws SQLException {
        driver.disconnect();
    }

    private boolean isSqlInjection(String s) {
        return s.contains("'\0") || s.contains("'")
                || s.contains("\"") || s.contains("\b")
                || s.contains("\n") || s.contains("\r")
                || s.contains("\t") || s.contains("" + (char) 26)
                || s.contains("\\") || s.contains("%")
                || s.contains("_") || s.contains("`"); //TODO проверить список опасных симвоолов
    }
}
