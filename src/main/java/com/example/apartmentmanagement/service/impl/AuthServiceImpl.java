package com.example.apartmentmanagement.service.impl;

import com.example.apartmentmanagement.dto.request.LoginRequest;
import com.example.apartmentmanagement.dto.request.RegisterRequest;
import com.example.apartmentmanagement.dto.response.JwtResponse;
import com.example.apartmentmanagement.entity.*;
import com.example.apartmentmanagement.enums.ErrorCode;
import com.example.apartmentmanagement.enums.RoleName;
import com.example.apartmentmanagement.exception.AppException;
import com.example.apartmentmanagement.repository.*;
import com.example.apartmentmanagement.service.AuthService;
import com.example.apartmentmanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final ResidentRepository residentRepository;
    private final LevelRepository levelRepository;
    private final PasswordEncoder passwordEncoder;
    //private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public void register(RegisterRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        //Lấy role từ request
        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        userRepository.save(user);

        if(request.getRole() == RoleName.ROLE_RESIDENT){
            Resident resident = new Resident();
            resident.setFullName(request.getFullName());
            resident.setEmail(request.getEmail());
            resident.setPhoneNum(request.getPhoneNum());
            resident.setIdNumber(request.getIdNumber());
            resident.setGender(request.getGender());
            resident.setDob(request.getDob());
            resident.setUser(user);
            residentRepository.save(resident);
        } else if (request.getRole() == RoleName.ROLE_EMPLOYEE) {
            if(request.getLevelId() == null){
                throw new AppException(ErrorCode.INVALID_LEVEL_REQUEST);
            }
            Level level = levelRepository.findById(request.getLevelId())
                    .orElseThrow(() -> new AppException(ErrorCode.LEVEL_NOT_FOUND));

            Employee employee = new Employee();
            employee.setFullName(request.getFullName());
            employee.setEmail(request.getEmail());
            employee.setPhoneNum(request.getPhoneNum());
            employee.setIdNumber(request.getIdNumber());
            employee.setPosition(request.getPosition());
            employee.setGender(request.getGender());
            employee.setDob(request.getDob());
            employee.setLevel(level);
            employee.setUser(user);
            employeeRepository.save(employee);
        }
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIAL));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIAL);
        }

        // Xây dựng danh sách authorities đầy đủ
        List<String> authorities = new ArrayList<>();
        authorities.add(user.getRole().getName().name()); // Thêm role

        // Nếu là EMPLOYEE, thêm authority cấp độ
        if (user.getRole().getName() == RoleName.ROLE_EMPLOYEE && user.getEmployee() != null) {
            Level level = user.getEmployee().getLevel();
            if (level != null) {
                String levelName = level.getName().trim().toUpperCase();
                authorities.add("LEVEL_" + levelName); // Thêm LEVEL_MANAGER, LEVEL_STAFF...
            }
        }

        String token = jwtUtil.generateToken(user.getUsername(), authorities);

        return new JwtResponse(token, user.getUsername(), authorities);
    }
}
