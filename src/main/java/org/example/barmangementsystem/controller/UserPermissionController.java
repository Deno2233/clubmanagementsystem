package org.example.barmangementsystem.controller;

import org.example.barmangementsystem.entity.User;
import org.example.barmangementsystem.entity.UserPermission;
import org.example.barmangementsystem.services.UserPermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@CrossOrigin(origins = "http://localhost:5173,http://192.168.100.3:4567")

public class UserPermissionController {

    private final UserPermissionService permissionService;

    public UserPermissionController(UserPermissionService permissionService) {
        this.permissionService = permissionService;
    }
    @PostMapping
    public UserPermission create(@RequestBody UserPermission UserPermission) {
        return permissionService.saveUser(UserPermission);
    }
    @GetMapping("/{userId}")
    public List<UserPermission> getPermissions(@PathVariable Long userId) {
        return permissionService.getPermissionsByUser(userId);
    }

    @PutMapping("/{userId}")
    public UserPermission updatePermissions(
            @PathVariable Long userId,
            @RequestBody UserPermission permissions) {
        return permissionService.updatePermissions(userId, permissions);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
       permissionService.delete(id);
    }
}
