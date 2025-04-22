package models;

public class User {
    private String username;
    private String passwordHash;
    private String Role;

    public User(String username, String passwordHash, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        Role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getpasswordHash() {
        return passwordHash;
    }

    public void setpasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public static User fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) return null;
        return new User(parts[0], parts[1], parts[2]);
    }

}
