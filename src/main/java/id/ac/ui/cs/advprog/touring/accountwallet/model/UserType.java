package id.ac.ui.cs.advprog.touring.accountwallet.model;

public enum UserType {
    ADMIN, CUSTOMER, TOURGUIDE;

    public String toString() {
        switch(this) {
            case ADMIN: return "Admin";
            case CUSTOMER: return "Customer";
            case TOURGUIDE: return "Tour Guide";
            default: throw new IllegalArgumentException();
        }
    }

    public static UserType fromString(String role) {
        for (UserType b : UserType.values()) {
            if (b.toString().equalsIgnoreCase(role)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No constant with role " + role + " found");
    }
}
