package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.request.FeeTypeRequest;
import com.example.apartmentmanagement.dto.response.FeeTypeResponse;
import com.example.apartmentmanagement.service.FeeTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fee-types")
public class FeeTypeController {

    private final FeeTypeService feeTypeService;

    @PostMapping
    public ResponseEntity<FeeTypeResponse> createFeeType(@RequestBody FeeTypeRequest request) {
        return ResponseEntity.ok(feeTypeService.createFeeType(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeeTypeResponse> updateFeeType(@PathVariable Long id, @RequestBody FeeTypeRequest request) {
        return ResponseEntity.ok(feeTypeService.updateFeeType(id, request));
    }

    @GetMapping
    public ResponseEntity<List<FeeTypeResponse>> getAllFeeTypes() {
        return ResponseEntity.ok(feeTypeService.getAllFeeTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeeTypeResponse> getFeeTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(feeTypeService.getFeeTypeById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeeType(@PathVariable Long id) {
        feeTypeService.deleteFeeType(id);
        return ResponseEntity.noContent().build();
    }
}
