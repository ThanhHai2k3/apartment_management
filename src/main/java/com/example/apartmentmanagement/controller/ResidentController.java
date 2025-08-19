package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.request.ResidentRequest;
import com.example.apartmentmanagement.dto.response.ResidentApartmentHistoryResponse;
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

    @GetMapping("/{residentId}/history")
    public ResponseEntity<List<ResidentApartmentHistoryResponse>> getResidentHistory(@PathVariable Long residentId){
        return ResponseEntity.ok(residentService.getResidentHistory(residentId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResident(@PathVariable Long id){
        residentService.deleteResident(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{residentId}/apartment")
    public ResponseEntity<Void> removeResidentFromApartment(@PathVariable Long residentId){
        residentService.removeResidentFromApartment(residentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{residentId}/apartment/{apartmentId}")
    public ResponseEntity<Void> assignResidentToApartment(@PathVariable Long residentId, @PathVariable Long apartmentId){
        residentService.assignResidentToApartment(residentId, apartmentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id1}/cohabiting-current/{id2}")
    public ResponseEntity<Boolean> areCurrentlyCohabiting(@PathVariable Long id1, @PathVariable Long id2){
        return ResponseEntity.ok(residentService.areResidentsCurrentCohabiting(id1, id2));
    }

    @GetMapping("/{id1}/cohabited-history/{id2}")
    public ResponseEntity<Boolean> haveEverCohabited(@PathVariable Long id1, @PathVariable Long id2){
        return ResponseEntity.ok(residentService.haveResidentsEverCohabited(id1, id2));
    }
}
