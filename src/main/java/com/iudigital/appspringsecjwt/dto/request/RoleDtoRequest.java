package com.iudigital.appspringsecjwt.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RoleDtoRequest{

    @NotNull(message = "Username is required DTO")
    @NotEmpty(message = "Username not be empty DTO")
    private String rol;

}
