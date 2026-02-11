package org.example.barmangementsystem.adminspass;

import org.example.barmangementsystem.entity.User;
import org.example.barmangementsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if admin exists
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode("admin123");
        if (userRepository.countByRole("ADMIN") == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPasswordHash(hashedPassword );
            admin.setRole("Admin");
            userRepository.save(admin);
            System.out.println("✅ Admin user created: username=admin, password=admin123");
        }
    }
}
