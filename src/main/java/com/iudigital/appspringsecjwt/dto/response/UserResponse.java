package com.iudigital.appspringsecjwt.dto.response;

import com.iudigital.appspringsecjwt.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse{

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;

    private LocalDate dateBirth;
    private boolean enabled;

    private List<Role> roles;
    private String image;

    private LocalDate createdAt;
    private LocalDate updatedAt;

}
