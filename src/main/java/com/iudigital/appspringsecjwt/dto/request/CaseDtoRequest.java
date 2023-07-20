package com.iudigital.appspringsecjwt.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CaseDtoRequest {

    @Size(max = 60, message = "Name must be max 60 characters")
    private String description;
    @Column(nullable = false)
    @NotNull(message = "DateCase is required")
    private LocalDate dateCase;
    @Column(nullable = false)
    @NotNull(message = "latitude is required")
    private Float latitude;
    @Column(nullable = false)
    @NotNull(message = "longitude is required")
    private Float longitude;
    @Column(nullable = false)
    @NotNull(message = "altitude is required")
    private Float altitude;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    @NotNull(message = "user_id is required")
    private Long user_id;
    @NotNull(message = "crime_id is required")
    private Long crime_id;

}
