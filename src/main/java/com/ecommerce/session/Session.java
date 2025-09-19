package com.ecommerce.session;

public class Session {
    public static String currentUser;
    private static String currentRole;
    private static String currentCustomerName;

    public static void setCurrentUsername(String username) {
        currentUser = username;
    }

    public static String getCurrentUsername() {
        return currentUser;
    }

    public static void setCurrentRole(String role) {
        currentRole = role;
    }

    public static String getCurrentRole() {
        return currentRole;
    }
    public static void setCurrentCustomerName(String customerName) {
        currentCustomerName = customerName;
    }
    public static String getCurrentCustomerName() {
        return currentCustomerName;
    }

    public static void clear() {
        currentUser = null;
        currentRole = null;
        currentCustomerName = null;
    }
}
