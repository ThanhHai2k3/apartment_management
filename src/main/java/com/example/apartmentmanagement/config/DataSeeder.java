package com.example.apartmentmanagement.config;

import com.example.apartmentmanagement.entity.Level;
import com.example.apartmentmanagement.entity.Role;
import com.example.apartmentmanagement.enums.RoleName;
import com.example.apartmentmanagement.repository.LevelRepository;
import com.example.apartmentmanagement.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final LevelRepository levelRepository;

    @Override
    public void run(String... args) {
        if(roleRepository.count() == 0){
            roleRepository.save(new Role(null, RoleName.ROLE_ADMIN, new ArrayList<>()));
            roleRepository.save(new Role(null, RoleName.ROLE_EMPLOYEE, new ArrayList<>()));
            roleRepository.save(new Role(null, RoleName.ROLE_RESIDENT, new ArrayList<>()));
        }

        if(levelRepository.count() == 0){
            levelRepository.save(new Level(null, "Junior", 1, new ArrayList<>()));
            levelRepository.save(new Level(null, "Mid", 2, new ArrayList<>()));
            levelRepository.save(new Level(null, "Senior", 3, new ArrayList<>()));
        }
    }
}
