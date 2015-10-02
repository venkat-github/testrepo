package com.test.app.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    private AuthoritiesConstants() {
    }

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String DOCTOR = "ROLE_DOCTOR";

    public static final String HOSPITAL = "ROLE_HOSPITAL_ADMIN";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
}
