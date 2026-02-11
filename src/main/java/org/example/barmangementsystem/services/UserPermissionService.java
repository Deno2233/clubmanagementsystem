package org.example.barmangementsystem.services;

import org.example.barmangementsystem.entity.User;
import org.example.barmangementsystem.entity.UserPermission;
import org.example.barmangementsystem.repository.UserPermissionRepository;
import org.example.barmangementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserPermissionService {

    private final UserPermissionRepository permissionRepository;
    private final UserRepository userRepository;

    public UserPermissionService(UserPermissionRepository permissionRepository, UserRepository userRepository) {
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
    }
    public  UserPermission saveUser(UserPermission UserPermission) {
        return permissionRepository.save(UserPermission);
    }
    public List<UserPermission> getPermissionsByUser(Long userId) {
        return permissionRepository.findByUserId(userId);
    }

    public UserPermission updatePermissions(Long userId, UserPermission newPermissions) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Remove old permissions
        permissionRepository.deleteByUserId(userId);

        // Save new ones

        return permissionRepository.save(newPermissions);
    }

    public boolean hasAccess(Long userId, String moduleName) {
        return permissionRepository.findByUserId(userId).stream()
                .anyMatch(p -> p.getModuleName().equalsIgnoreCase(moduleName) && p.isCanAccess());
    }

    public void delete(Long id) {
        permissionRepository.deleteById(id);
    }
}
