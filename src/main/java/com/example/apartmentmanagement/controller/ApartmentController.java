package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.request.ApartmentRequest;
import com.example.apartmentmanagement.dto.response.ApartmentResponse;
import com.example.apartmentmanagement.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apartments")

public class ApartmentController {
    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @PostMapping
    public ResponseEntity<ApartmentResponse> createApartment(@RequestBody ApartmentRequest request){
        return ResponseEntity.ok(apartmentService.createApartment(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApartmentResponse> updateApartment(@PathVariable Long id, @RequestBody ApartmentRequest request){
        return ResponseEntity.ok(apartmentService.updateApartment(id, request));
    }

    @GetMapping
    public ResponseEntity<List<ApartmentResponse>> getAllApartments(){
        return ResponseEntity.ok(apartmentService.getAllApartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartmentResponse> getApartmentById(@PathVariable Long id){
        return ResponseEntity.ok(apartmentService.getApartmentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApartment(@PathVariable Long id){
        apartmentService.deleteApartment(id);
        return ResponseEntity.ok("Apartment deleted successfully");
    }
}
