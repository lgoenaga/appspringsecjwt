package com.iudigital.appspringsecjwt.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CrimeDtoRequest {


    @NotNull(message = "name is required")
    @NotEmpty(message = "name is required")
    private String name;
    private String description;

    @JsonProperty("user_id")
    @NotNull(message = "user_id is required")
    private Long userId;

    private LocalDate createdAt;
    private LocalDate updatedAt;

}
