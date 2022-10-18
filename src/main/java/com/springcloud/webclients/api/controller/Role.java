package com.springcloud.webclients.api.controller;

public enum Role {
    ADMIN(1),
    USER(2),
    ;

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static Role getEnumLevel(int val){
        switch (val){
            case 1 :
                return ADMIN;
            case 2 :
                return USER;
            default:
                throw new IllegalArgumentException("not found level : " + val);
        }
    }
}
