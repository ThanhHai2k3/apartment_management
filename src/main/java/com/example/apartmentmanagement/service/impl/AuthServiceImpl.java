package com.example.apartmentmanagement.service.impl;

import com.example.apartmentmanagement.dto.request.LoginRequest;
import com.example.apartmentmanagement.dto.request.RegisterRequest;
import com.example.apartmentmanagement.dto.response.JwtResponse;
import com.example.apartmentmanagement.entity.Role;
import com.example.apartmentmanagement.entity.User;
import com.example.apartmentmanagement.enums.RoleName;
import com.example.apartmentmanagement.repository.RoleRepository;
import com.example.apartmentmanagement.repository.UserRepository;
import com.example.apartmentmanagement.service.AuthService;
import com.example.apartmentmanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(UserRepository userRepo, RoleRepository roleRepo,
                           PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void register(RegisterRequest req) {
        if (userRepo.existsByUsername(req.username)) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(req.username);
        user.setEmail(req.email);
        user.setPassword(passwordEncoder.encode(req.password));

        Role userRole = roleRepo.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRoles(Set.of(userRole));
        userRepo.save(user);
    }

    @Override
    public JwtResponse login(LoginRequest req) {
        User user = userRepo.findByUsername(req.username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(req.password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());

        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList());

        return new JwtResponse(token, user.getUsername(), roles);
    }
}
