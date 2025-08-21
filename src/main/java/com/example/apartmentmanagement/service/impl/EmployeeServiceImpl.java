package com.example.apartmentmanagement.service.impl;

import com.example.apartmentmanagement.dto.request.EmployeeRequest;
import com.example.apartmentmanagement.dto.response.EmployeeResponse;
import com.example.apartmentmanagement.dto.response.EmployeeSummaryResponse;
import com.example.apartmentmanagement.entity.Employee;
import com.example.apartmentmanagement.entity.Level;
import com.example.apartmentmanagement.entity.User;
import com.example.apartmentmanagement.enums.ErrorCode;
import com.example.apartmentmanagement.enums.RoleName;
import com.example.apartmentmanagement.exception.AppException;
import com.example.apartmentmanagement.mapper.EmployeeMapper;
import com.example.apartmentmanagement.repository.EmployeeRepository;
import com.example.apartmentmanagement.repository.LevelRepository;
import com.example.apartmentmanagement.repository.ResidentRepository;
import com.example.apartmentmanagement.repository.UserRepository;
import com.example.apartmentmanagement.security.UserDetailsImpl;
import com.example.apartmentmanagement.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final LevelRepository levelRepository;
    private final UserRepository userRepository;
    private final ResidentRepository residentRepository;

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request){
        Employee employee = employeeMapper.toEntity(request);

        Level level = levelRepository.findById(request.getLevelId())
                .orElseThrow(() -> new AppException(ErrorCode.LEVEL_NOT_FOUND));
        employee.setLevel(level);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        employee.setUser(user);

        Employee employeeSaved = employeeRepository.save(employee);
        return employeeMapper.toResponse(employeeSaved);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        return employeeMapper.toResponse(employee);
    }

    @Override
    public List<EmployeeSummaryResponse> getAllEmployees(){
        return employeeMapper.toSummaryList(employeeRepository.findAll());
    }

    @Transactional
    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        // Lấy thông tin user hiện tại từ SecurityContext
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean isSelf = auth.getPrincipal() instanceof UserDetailsImpl userDetails &&
                userDetails.getEmployeeId() != null && userDetails.getEmployeeId().equals(id);

        if(request.getIdNumber() != null){
            if (!request.getIdNumber().equals(employee.getIdNumber()) &&
                    (employeeRepository.existsByIdNumber(request.getIdNumber()) ||
                            residentRepository.existsByIdNumber(request.getIdNumber()))) {
                throw new AppException(ErrorCode.ID_NUMBER_EXISTED);
            }
            employee.setIdNumber(request.getIdNumber());
        }

        employeeMapper.updateEntityFromRequest(employee, request);

        if(isAdmin){
            if (request.getLevelId() != null) {
                Level level = levelRepository.findById(request.getLevelId())
                        .orElseThrow(() -> new AppException(ErrorCode.LEVEL_NOT_FOUND));
                employee.setLevel(level);
            }

            if (request.getUserId() != null) {
                User user = userRepository.findById(request.getUserId())
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

                if (!employee.getUser().getId().equals(request.getUserId())) {
                    if (employeeRepository.existsByUserIdAndIdNot(request.getUserId(), id)) {
                        throw new AppException(ErrorCode.USER_ALREADY_LINKED);
                    }
                    if (residentRepository.existsByUserId(request.getUserId())) {
                        throw new AppException(ErrorCode.USER_ALREADY_LINKED);
                    }
                    if (user.getRole().getName().equals(RoleName.ROLE_ADMIN)) {
                        throw new AppException(ErrorCode.USER_ROLE_ADMIN_NOT_ALLOWED);
                    }
                }
                employee.setUser(user);
            }
        }
        else if (isSelf && (request.getLevelId() != null || request.getUserId() != null)){
            throw new AppException(ErrorCode.EMPLOYEE_ACCESS_DENIED);
        }

        employeeRepository.save(employee);

        return employeeMapper.toResponse(employee);
    }

    @Transactional
    @Override
    public void deleteEmployee(Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        Long userId = employee.getUser().getId();

        employeeRepository.deleteById(id);
        userRepository.deleteById(userId);
    }
}
