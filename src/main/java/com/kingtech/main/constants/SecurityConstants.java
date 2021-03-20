package com.kingtech.main.constants;

import java.util.concurrent.TimeUnit;

public class SecurityConstants {

    public static final String SECRET = "SECRET_KEY";
    public static final long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(15); // 15 mins
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/signup";
    public static final String LOGIN_URL = "/users/login";
}
