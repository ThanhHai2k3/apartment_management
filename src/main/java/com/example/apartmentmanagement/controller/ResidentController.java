package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.request.ResidentRequest;
import com.example.apartmentmanagement.dto.response.ResidentApartmentHistoryResponse;
import com.example.apartmentmanagement.dto.response.ResidentResponse;
import com.example.apartmentmanagement.service.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residents")
@RequiredArgsConstructor
public class ResidentController {
    private final ResidentService residentService;

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('LEVEL_MANAGER','LEVEL_STAFF')")
    @PostMapping
    public ResponseEntity<ResidentResponse> createResident(@RequestBody ResidentRequest request){
        return ResponseEntity.ok(residentService.createResident(request));
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('LEVEL_MANAGER','LEVEL_STAFF') or #id == authentication.principal.residentId")
    @PutMapping("/{id}")
    public ResponseEntity<ResidentResponse> updateResident(@PathVariable Long id, @RequestBody ResidentRequest request){
        return ResponseEntity.ok(residentService.updateResident(id, request));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping
    public ResponseEntity<List<ResidentResponse>> getAllResidents(){
        return ResponseEntity.ok(residentService.getAllResidents());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE') or #id == authentication.principal.residentId")
    @GetMapping("/{id}")
    public ResponseEntity<ResidentResponse> getResidentById(@PathVariable Long id){
        return ResponseEntity.ok(residentService.getResidentById(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE') or #residentId == authentication.principal.residentId")
    @GetMapping("/{residentId}/history")
    public ResponseEntity<List<ResidentApartmentHistoryResponse>> getResidentHistory(@PathVariable Long residentId){
        return ResponseEntity.ok(residentService.getResidentHistory(residentId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResident(@PathVariable Long id){
        residentService.deleteResident(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('LEVEL_MANAGER','LEVEL_STAFF')")
    @DeleteMapping("/{residentId}/apartment")
    public ResponseEntity<Void> removeResidentFromApartment(@PathVariable Long residentId){
        residentService.removeResidentFromApartment(residentId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasAnyAuthority('LEVEL_MANAGER','LEVEL_STAFF')")
    @PostMapping("/{residentId}/apartment/{apartmentId}")
    public ResponseEntity<Void> assignResidentToApartment(@PathVariable Long residentId, @PathVariable Long apartmentId){
        residentService.assignResidentToApartment(residentId, apartmentId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping("/{id1}/cohabiting-current/{id2}")
    public ResponseEntity<Boolean> areCurrentlyCohabiting(@PathVariable Long id1, @PathVariable Long id2){
        return ResponseEntity.ok(residentService.areResidentsCurrentCohabiting(id1, id2));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    @GetMapping("/{id1}/cohabited-history/{id2}")
    public ResponseEntity<Boolean> haveEverCohabited(@PathVariable Long id1, @PathVariable Long id2){
        return ResponseEntity.ok(residentService.haveResidentsEverCohabited(id1, id2));
    }
}
