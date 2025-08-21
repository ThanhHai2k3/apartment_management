package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.request.EmployeeRequest;
import com.example.apartmentmanagement.dto.request.EmployeeSelfUpdateRequest;
import com.example.apartmentmanagement.dto.response.EmployeeResponse;
import com.example.apartmentmanagement.dto.response.EmployeeSummaryResponse;
import com.example.apartmentmanagement.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('LEVEL_MANAGER')")
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest request){
        return ResponseEntity.ok(employeeService.createEmployee(request));
    }

    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.employeeId")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<EmployeeSummaryResponse>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployeeByAdmin(@PathVariable Long id, @RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.updateEmployeeByAdmin(id, request));
    }

    @PreAuthorize("#id == authentication.principal.employeeId")
    @PutMapping("/self/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployeeSelf(@PathVariable Long id, @RequestBody EmployeeSelfUpdateRequest request) {
        return ResponseEntity.ok(employeeService.updateEmployeeSelf(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
