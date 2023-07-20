package com.iudigital.appspringsecjwt.dto.response;


import com.iudigital.appspringsecjwt.model.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UserDtoResponse {

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
