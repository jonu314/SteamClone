package com.Bug_Tracker.constant;

public class Authority {
    public static final String[] USER_AUTHORITIES = { "user:create", "user:update" };
    public static final String[] MANAGER_AUTHORITIES = { "project:create","project:assign" };
    public static final String[] ADMIN_AUTHORITIES = { "admin:delete", "admin:login", "admin:update" };

}
