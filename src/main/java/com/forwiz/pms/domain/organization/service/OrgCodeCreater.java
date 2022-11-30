package com.forwiz.pms.domain.organization.service;

public class OrgCodeCreater {

    public static char[] CH =
            {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P'}
            ;


    public static String make(String name){

        int length = name.length();
        if(length >= CH.length) length = CH.length-1;

        String num = String.format("%04d", name.charAt(0)+length);

        return CH[length] + num;
    }

}
