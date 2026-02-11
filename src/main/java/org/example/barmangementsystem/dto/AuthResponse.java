package org.example.barmangementsystem.dto;



import java.util.List;

public class AuthResponse {
    private String token;
    private String username;
    private long userId;
    private String role;
    private List<String> permissions;

    public AuthResponse() {}

    public AuthResponse(String token, String username, long userId, String role, List<String> permissions) {
        this.token = token;
        this.username = username;
        this.userId = userId;
        this.role = role;
        this.permissions = permissions;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public List<String> getPermissions() { return permissions; }
    public void setPermissions(List<String> permissions) { this.permissions = permissions; }
}