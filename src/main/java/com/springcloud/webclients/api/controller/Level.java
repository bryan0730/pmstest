package com.springcloud.webclients.api.controller;

public enum Level {
    ADMIN(1),
    FORWIZ(2),
    KERIS(3)
    ;

    private final int value;

    Level(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static Level getEnumLevel(int val){
        switch (val){
            case 1 :
                return ADMIN;
            case 2 :
                return FORWIZ;
            case 3 :
                return KERIS;
            default:
                throw new IllegalArgumentException("not found level : " + val);
        }
    }
}
