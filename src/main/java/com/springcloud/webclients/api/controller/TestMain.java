package com.springcloud.webclients.api.controller;

public class TestMain {
    public static void main(String[] args) {
        String name = Level.getEnumLevel(1).name();
        System.out.println("name = " + name);
    }
}
