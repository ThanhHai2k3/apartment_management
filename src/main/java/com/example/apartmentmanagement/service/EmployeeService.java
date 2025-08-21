package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.dto.request.EmployeeRequest;
import com.example.apartmentmanagement.dto.request.EmployeeSelfUpdateRequest;
import com.example.apartmentmanagement.dto.response.EmployeeResponse;
import com.example.apartmentmanagement.dto.response.EmployeeSummaryResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse createEmployee(EmployeeRequest request);
    EmployeeResponse getEmployeeById(Long id);
    List<EmployeeSummaryResponse> getAllEmployees();
    EmployeeResponse updateEmployeeByAdmin(Long id, EmployeeRequest request);
    EmployeeResponse updateEmployeeSelf(Long id, EmployeeSelfUpdateRequest request);
    void deleteEmployee(Long id);
}
