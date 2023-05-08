package com.api.bigu.models.enums;

public enum UserType {
    DRIVER("DRIVER"),
    RIDER("RIDER");

    private String name;

    UserType(String name) {
    }

    public String getName(){
        return this.name;
    }
}
