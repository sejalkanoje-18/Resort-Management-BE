package com.ResortManagementBE.RRMS.security.config;

import com.ResortManagementBE.RRMS.auth.entity.Role;
import com.ResortManagementBE.RRMS.auth.entity.User;
import com.ResortManagementBE.RRMS.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SuperAdminSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.findByUsername("superadmin").isEmpty()) {
            User superAdmin = new User();
            superAdmin.setUsername("superadmin");
            superAdmin.setPassword(passwordEncoder.encode("Admin@1234"));
            superAdmin.setRole(Role.SUPER_ADMIN);
            userRepository.save(superAdmin);
            log.info("Super admin user created with username: superadmin and password: Admin@1234");
        } else {
            log.info("Super admin user already exists, skipping seeding.");
        }
    }
}
