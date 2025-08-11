package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.request.ResidentRequest;
import com.example.apartmentmanagement.dto.response.ResidentResponse;
import com.example.apartmentmanagement.service.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residents")
@RequiredArgsConstructor
public class ResidentController {
    private final ResidentService residentService;

    @PostMapping
    public ResponseEntity<ResidentResponse> createResident(@RequestBody ResidentRequest request){
        return ResponseEntity.ok(residentService.createResident(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResidentResponse> updateResident(@PathVariable Long id, @RequestBody ResidentRequest request){
        return ResponseEntity.ok(residentService.updateResident(id, request));
    }

    @GetMapping
    public ResponseEntity<List<ResidentResponse>> getAllResidents(){
        return ResponseEntity.ok(residentService.getAllResidents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResidentResponse> getResidentById(@PathVariable Long id){
        return ResponseEntity.ok(residentService.getResidentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResident(@PathVariable Long id){
        residentService.deleteResident(id);
        return ResponseEntity.noContent().build();
    }
}
