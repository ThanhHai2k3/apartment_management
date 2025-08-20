package com.example.apartmentmanagement.config;

import com.example.apartmentmanagement.repository.ResidentApartmentHistoryRepository;
import com.example.apartmentmanagement.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component("customSecurity")
@RequiredArgsConstructor
public class CustomSecurityExpression {
    private final ResidentApartmentHistoryRepository historyRepository;

    public boolean isResidentLinkedToApartment(Authentication authentication, Long apartmentId){
        //Lấy UserDetailsImpl từ authentication
        if(!(authentication.getPrincipal() instanceof UserDetailsImpl userDetails)){
            return false;
        }

        //Áp dụng cho resident
        if(!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_RESIDENT"))){
            return false;
        }

        Long residentId = userDetails.getResidentId();
        if(residentId == null){
            return false;
        }

        return historyRepository.existsByResidentIdAndApartmentIdAndEndDateIsNull(residentId, apartmentId);
    }
}
