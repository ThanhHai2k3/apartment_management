package com.example.apartmentmanagement.config;

import com.example.apartmentmanagement.entity.Level;
import com.example.apartmentmanagement.entity.Role;
import com.example.apartmentmanagement.entity.User;
import com.example.apartmentmanagement.enums.ErrorCode;
import com.example.apartmentmanagement.enums.RoleName;
import com.example.apartmentmanagement.exception.AppException;
import com.example.apartmentmanagement.repository.LevelRepository;
import com.example.apartmentmanagement.repository.RoleRepository;
import com.example.apartmentmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final LevelRepository levelRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if(roleRepository.count() == 0){
            roleRepository.save(new Role(null, RoleName.ROLE_ADMIN, new ArrayList<>()));
            roleRepository.save(new Role(null, RoleName.ROLE_EMPLOYEE, new ArrayList<>()));
            roleRepository.save(new Role(null, RoleName.ROLE_RESIDENT, new ArrayList<>()));
        }

        if(levelRepository.count() == 0){
            levelRepository.save(new Level(null, "Manager", 1, new ArrayList<>()));
            levelRepository.save(new Level(null, "Staff", 2, new ArrayList<>()));
            levelRepository.save(new Level(null, "Technician", 3, new ArrayList<>()));
            levelRepository.save(new Level(null, "Security", 4, new ArrayList<>()));
        }

        if (userRepository.findByUsername("admin").isEmpty()){
            Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(adminRole);

            userRepository.save(admin);
        }
    }
}
