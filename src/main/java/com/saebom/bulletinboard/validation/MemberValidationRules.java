package com.saebom.bulletinboard.validation;

public class MemberValidationRules {

    private MemberValidationRules() {

    }

    // username
    public static final int USERNAME_MIN = 5;
    public static final int USERNAME_MAX = 15;

    // password
    public static final int PASSWORD_MIN = 8;
    public static final int PASSWORD_MAX = 16;

    // name
    public static final int NAME_MIN = 2;
    public static final int NAME_MAX = 4;

}