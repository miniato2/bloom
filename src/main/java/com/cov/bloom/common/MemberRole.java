package com.cov.bloom.common;

public enum MemberRole {
    c("c"),

    m("m"),

    s("s");

    private String role;

    MemberRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "role='" + role + '\'' +
                '}';
    }

}
