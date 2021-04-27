package org.dev.scud.orm.interfaces;

import org.dev.scud.models.User;

import java.sql.SQLException;
import java.util.UUID;

public interface UserModelConnectorInterface {
    public User[] getAllUsers() throws SQLException;

    public User getUser(String id) throws SQLException, IllegalAccessException;

    public User getUser(UUID id) throws SQLException, IllegalAccessException;

    public boolean createUser(String id, String name) throws SQLException, IllegalAccessException;

    public boolean createUser(UUID id, String name) throws SQLException, IllegalAccessException;

    public boolean createUser(User user) throws SQLException, IllegalAccessException;

    public boolean updateUser(String id, String name) throws SQLException, IllegalAccessException;

    public boolean updateUser(UUID id, String name) throws SQLException, IllegalAccessException;

    public boolean updateUser(User user) throws SQLException, IllegalAccessException;

    public boolean deleteUser(String id) throws SQLException, IllegalAccessException;

    public boolean deleteUser(UUID id) throws SQLException, IllegalAccessException;

    public boolean deleteUser(User user) throws SQLException, IllegalAccessException;

    public void disconnect() throws SQLException;
}
