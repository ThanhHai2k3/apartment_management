package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.request.FeeTypeRequest;
import com.example.apartmentmanagement.dto.response.FeeTypeResponse;
import com.example.apartmentmanagement.service.FeeTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fee-types")
public class FeeTypeController {

    private final FeeTypeService feeTypeService;

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('LEVEL_MANAGER')")
    @PostMapping
    public ResponseEntity<FeeTypeResponse> createFeeType(@RequestBody FeeTypeRequest request) {
        return ResponseEntity.ok(feeTypeService.createFeeType(request));
    }

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('LEVEL_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<FeeTypeResponse> updateFeeType(@PathVariable Long id, @RequestBody FeeTypeRequest request) {
        return ResponseEntity.ok(feeTypeService.updateFeeType(id, request));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping
    public ResponseEntity<List<FeeTypeResponse>> getAllFeeTypes() {
        return ResponseEntity.ok(feeTypeService.getAllFeeTypes());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<FeeTypeResponse> getFeeTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(feeTypeService.getFeeTypeById(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('LEVEL_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeeType(@PathVariable Long id) {
        feeTypeService.deleteFeeType(id);
        return ResponseEntity.noContent().build();
    }
}
