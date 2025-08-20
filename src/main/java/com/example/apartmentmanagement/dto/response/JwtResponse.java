package com.example.apartmentmanagement.dto.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    @JsonProperty("token")
    private String token;
    @JsonProperty("username")
    private String username;
    @JsonProperty("roles")
    private List<String> authorities;
}