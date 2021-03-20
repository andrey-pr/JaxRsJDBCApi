package org.dev.scud.debug;

import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

import org.dev.scud.orm.ModelConnector;

//TODO написать тесты
public class DebugMain {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ModelConnector mc = new ModelConnector();
        //System.out.println(mc.createUser(UUID.randomUUID(), "name"));
        //System.out.println(mc.getUser("289dbe8a-7513-46be-ad42-42d58fe3991f"));
        System.out.println(Arrays.toString(mc.getAllUsers()));
        //System.out.println(mc.deleteUser
        //                              (UUID.fromString("210ef507-9b48-4ccb-9018-668e62271c34")));
        System.out.println(mc.updateUser(
                UUID.fromString("65e9aef2-5637-47cc-989e-f12192346ebc"), "1"));
        Gson gson = new Gson();
        System.out.println(gson.toJson(mc.getAllUsers()));
        mc.disconnect();
    }
}
