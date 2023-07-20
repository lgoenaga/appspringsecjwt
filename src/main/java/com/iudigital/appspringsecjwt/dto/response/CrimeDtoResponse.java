package com.iudigital.appspringsecjwt.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CrimeDtoResponse {

    private Long id;
    private String name;
    private String description;
    private Long userId;

}
