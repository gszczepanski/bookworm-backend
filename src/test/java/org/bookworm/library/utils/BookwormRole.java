package org.bookworm.library.utils;

public enum BookwormRole {
    EDITOR("EDITOR"),
    EMPTY("EMPTY");

    private String name;

    BookwormRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
