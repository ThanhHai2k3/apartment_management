package com.example.apartmentmanagement.security;

import com.example.apartmentmanagement.entity.Level;
import com.example.apartmentmanagement.entity.User;
import com.example.apartmentmanagement.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private Long residentId; // Thêm: resident.id
    private Long employeeId;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities; //quyền của user

    public static UserDetailsImpl build(User user){
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 1. Add authority từ Role
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName().name()));

        Long residentId = null;
        Long employeeId = null;

        // 2. Nếu là EMPLOYEE thì add thêm authority theo Level
        if (user.getRole().getName().equals(RoleName.ROLE_EMPLOYEE) && user.getEmployee() != null) {
            Level level = user.getEmployee().getLevel();
            if (level != null) {
                authorities.add(new SimpleGrantedAuthority("LEVEL_" + level.getName().trim().toUpperCase()));
            }
            employeeId = user.getEmployee().getId();
        } else if (user.getRole().getName().equals(RoleName.ROLE_RESIDENT) && user.getResident() != null) {
            residentId = user.getResident().getId();
        }

        //System.out.println("DEBUG Authorities for user " + user.getUsername() + ": " + authorities);
        return new UserDetailsImpl(
                user.getId(),
                residentId,
                employeeId,
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }
}
