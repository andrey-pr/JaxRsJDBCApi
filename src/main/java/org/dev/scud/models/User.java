package org.dev.scud.models;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
