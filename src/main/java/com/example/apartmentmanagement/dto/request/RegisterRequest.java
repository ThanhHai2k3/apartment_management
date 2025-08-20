package com.example.apartmentmanagement.dto.request;

import com.example.apartmentmanagement.entity.Role;
import com.example.apartmentmanagement.enums.Gender;
import com.example.apartmentmanagement.enums.RoleName;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    //Thông tin login chung
    private String username;
    private String password;
    private RoleName role;

    //Thông tin cá nhân chung
    private String fullName;
    private String email;
    private String phoneNum;
    private Gender gender;
    private String idNumber;
    private LocalDate dob;

    //Thông tin riêng của Employee
    private String position;
    private Long levelId;
}
