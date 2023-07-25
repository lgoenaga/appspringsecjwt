package com.iudigital.appspringsecjwt.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RoleDtoRequest{

    @NotNull(message = "Username is required DTO")
    @NotEmpty(message = "Username not be empty DTO")
    @Size(min = 8, message = "Rol must be at least 8 characters")
    private String rol;

}
