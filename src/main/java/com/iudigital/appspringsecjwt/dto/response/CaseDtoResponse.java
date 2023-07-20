package com.iudigital.appspringsecjwt.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class CaseDtoResponse {

    private Long id;
    private String description;
    private LocalDate dateCase;
    private Float latitude;
    private Float longitude;
    private Float altitude;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Long user_id;
    private String user_name;
    private Long crime_id;
    private String crime_name;
}
