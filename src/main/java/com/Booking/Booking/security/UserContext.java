package com.Booking.Booking.security;

public class UserContext {
    private static final ThreadLocal<Long> userIdHolder = new ThreadLocal<>();

    public static void setUserId(long userId) {
        userIdHolder.set(userId);
    }

    public static long getUserId() {
        return userIdHolder.get();
    }
    public static void clear() {
        userIdHolder.remove();
    }
}
