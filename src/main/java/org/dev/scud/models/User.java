package org.dev.scud.models;

import java.util.UUID;

public class User {
    public UUID id;
    public String name;

    public User(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String id, String name) {
        this.id = UUID.fromString(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", name='"
                + name + '\''
                + '}';
    }
}
