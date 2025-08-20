package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.request.ApartmentRequest;
import com.example.apartmentmanagement.dto.response.ApartmentResponse;
import com.example.apartmentmanagement.dto.response.ResidentResponse;
import com.example.apartmentmanagement.service.ApartmentService;
import com.example.apartmentmanagement.service.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apartments")
@RequiredArgsConstructor
public class ApartmentController {
    private final ApartmentService apartmentService;
    private final ResidentService residentService;

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('LEVEL_MANAGER')")
    @PostMapping
    public ResponseEntity<ApartmentResponse> createApartment(@RequestBody ApartmentRequest request){
        return ResponseEntity.ok(apartmentService.createApartment(request));
    }

    @PreAuthorize("hasRole('ADMIN') or hasAuthority('LEVEL_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApartmentResponse> updateApartment(@PathVariable Long id, @RequestBody ApartmentRequest request){
        return ResponseEntity.ok(apartmentService.updateApartment(id, request));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping
    public ResponseEntity<List<ApartmentResponse>> getAllApartments(){
        return ResponseEntity.ok(apartmentService.getAllApartments());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE') or @customSecurity.isResidentLinkedToApartment(authentication, #id)")
    @GetMapping("/{id}")
    public ResponseEntity<ApartmentResponse> getApartmentById(@PathVariable Long id){
        return ResponseEntity.ok(apartmentService.getApartmentById(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE') or @customSecurity.isResidentLinkedToApartment(authentication, #apartmentId)")
    @GetMapping("/{apartmentId}/residents")
    public ResponseEntity<List<ResidentResponse>> getResidentsByApartment(@PathVariable Long apartmentId){
        return ResponseEntity.ok(residentService.getCurrentResidentsByApartment(apartmentId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApartment(@PathVariable Long id){
        apartmentService.deleteApartment(id);
        return ResponseEntity.ok("Apartment deleted successfully");
    }
}
