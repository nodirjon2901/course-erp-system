package org.example.courseerpsystem.domain.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
public enum Permission {

    USER_BLOCK("ADMIN"),

    USER_DELETE("ADMIN"),

    USER_UPDATE("ADMIN");

    private String role;

    Permission(String role) {
        this.role = role;
    }

    public List<Permission> getByRole(String role) {
        return Arrays.stream(Permission.values())
                .filter(permission -> Objects.equals(permission.role, role))
                .toList();
    }

}
