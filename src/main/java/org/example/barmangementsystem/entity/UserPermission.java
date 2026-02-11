package org.example.barmangementsystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_permission")
public class UserPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String moduleName; // e.g. "dashboard", "finance", "sales"
    private boolean canAccess = true;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserPermission() {
    }

    public UserPermission(Long id, String moduleName, boolean canAccess, User user) {
        this.id = id;
        this.moduleName = moduleName;
        this.canAccess = canAccess;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public boolean isCanAccess() {
        return canAccess;
    }

    public void setCanAccess(boolean canAccess) {
        this.canAccess = canAccess;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
