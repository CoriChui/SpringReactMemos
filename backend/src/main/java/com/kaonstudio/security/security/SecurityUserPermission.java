package com.kaonstudio.security.security;

public enum SecurityUserPermission {

    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String permission;

    public String getPermission() {
        return permission;
    }

    SecurityUserPermission(String permission) {
        this.permission = permission;
    }
}
