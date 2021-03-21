package org.dev.scud.servlets.jaxrslets;

import com.google.gson.Gson;
import org.dev.scud.models.User;
import org.dev.scud.orm.ModelConnector;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsersResourceTest {

    @Test
    void getAllUsers() throws SQLException, ClassNotFoundException {
        ModelConnector mc = new ModelConnector();
        mc.getAllUsers();
        mc.disconnect();
    }

    @Test
    void getAllUsersContentTest()
            throws SQLException, ClassNotFoundException, IllegalAccessException {
        ModelConnector mc = new ModelConnector();
        User user = new User(UUID.randomUUID(), "Swift");
        mc.createUser(user);
        boolean contains = Arrays.asList(mc.getAllUsers()).contains(user);
        mc.deleteUser(user);
        mc.disconnect();
        assertTrue(contains);
    }

    @Test
    void getUser() throws SQLException, ClassNotFoundException, IllegalAccessException {
        ModelConnector mc = new ModelConnector();
        User user = new User(UUID.randomUUID(), "Swift");
        mc.createUser(user);
        boolean contains = mc.getUser(user.id).equals(user);
        mc.deleteUser(user);
        mc.disconnect();
        assertTrue(contains);
    }

    @Test
    void getUserNull() throws SQLException, ClassNotFoundException, IllegalAccessException {
        ModelConnector mc = new ModelConnector();
        boolean isNull = mc.getUser(UUID.randomUUID()) == null;
        mc.disconnect();
        assertTrue(isNull);
    }

    @Test
    void getUserSqlInjection() throws SQLException, ClassNotFoundException {
        ModelConnector mc = new ModelConnector();
        boolean isBlocked;
        try {
            mc.getUser("'");
            isBlocked = false;
        } catch (IllegalAccessException e) {
            isBlocked = true;
        }
        mc.disconnect();
        assertTrue(isBlocked);
    }

    @Test
    void getUserIncorrectUuid()
            throws SQLException, ClassNotFoundException, IllegalAccessException {
        ModelConnector mc = new ModelConnector();
        boolean isBlocked;
        try {
            mc.getUser("1");
            isBlocked = false;
        } catch (IllegalArgumentException e) {
            isBlocked = true;
        }
        mc.disconnect();
        assertTrue(isBlocked);
    }

    @Test
    void addUser() throws SQLException, ClassNotFoundException, IllegalAccessException {
        ModelConnector mc = new ModelConnector();
        User user = new User(UUID.randomUUID(), "Swift");
        mc.createUser(user);
        boolean contains = mc.getUser(user.id).equals(user);
        mc.deleteUser(user);
        mc.disconnect();
        assertTrue(contains);
    }

    @Test
    void addUserSqlInjection() throws SQLException, ClassNotFoundException {
        ModelConnector mc = new ModelConnector();
        boolean isBlocked;
        try {
            mc.createUser("'", "'");
            isBlocked = false;
        } catch (IllegalAccessException e) {
            isBlocked = true;
        }
        mc.disconnect();
        assertTrue(isBlocked);
    }

    @Test
    void addUserIncorrectUuid()
            throws SQLException, ClassNotFoundException, IllegalAccessException {
        ModelConnector mc = new ModelConnector();
        boolean isBlocked;
        try {
            mc.createUser("1", "1");
            isBlocked = false;
        } catch (IllegalArgumentException e) {
            isBlocked = true;
        }
        mc.disconnect();
        assertTrue(isBlocked);
    }

    @Test
    void updateUser() throws SQLException, ClassNotFoundException, IllegalAccessException {
        ModelConnector mc = new ModelConnector();
        User user = new User(UUID.randomUUID(), "Swift");
        mc.createUser(user);
        user.name = "John";
        mc.updateUser(user);
        boolean contains = mc.getUser(user.id).equals(user);
        mc.deleteUser(user);
        mc.disconnect();
        assertTrue(contains);
    }

    @Test
    void updateUserSqlInjection() throws SQLException, ClassNotFoundException {
        ModelConnector mc = new ModelConnector();
        boolean isBlocked;
        try {
            mc.updateUser("'", "'");
            isBlocked = false;
        } catch (IllegalAccessException e) {
            isBlocked = true;
        }
        mc.disconnect();
        assertTrue(isBlocked);
    }

    @Test
    void updateUserIncorrectUuid()
            throws SQLException, ClassNotFoundException, IllegalAccessException {
        ModelConnector mc = new ModelConnector();
        boolean isBlocked;
        try {
            mc.updateUser("1", "1");
            isBlocked = false;
        } catch (IllegalArgumentException e) {
            isBlocked = true;
        }
        mc.disconnect();
        assertTrue(isBlocked);
    }

    @Test
    void deleteUser() throws SQLException, ClassNotFoundException, IllegalAccessException {
        ModelConnector mc = new ModelConnector();
        User user = new User(UUID.randomUUID(), "Swift");
        mc.createUser(user);
        boolean contains = mc.getUser(user.id).equals(user);
        mc.deleteUser(user);
        mc.disconnect();
        assertTrue(contains);
    }

    @Test
    void deleteUserSqlInjection() throws SQLException, ClassNotFoundException {
        ModelConnector mc = new ModelConnector();
        boolean isBlocked;
        try {
            mc.deleteUser("'");
            isBlocked = false;
        } catch (IllegalAccessException e) {
            isBlocked = true;
        }
        mc.disconnect();
        assertTrue(isBlocked);
    }

    @Test
    void deleteUserIncorrectUuid()
            throws SQLException, ClassNotFoundException, IllegalAccessException {
        ModelConnector mc = new ModelConnector();
        boolean isBlocked;
        try {
            mc.deleteUser("1");
            isBlocked = false;
        } catch (IllegalArgumentException e) {
            isBlocked = true;
        }
        mc.disconnect();
        assertTrue(isBlocked);
    }
}