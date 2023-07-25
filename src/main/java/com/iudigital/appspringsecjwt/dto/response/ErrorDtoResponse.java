package com.iudigital.appspringsecjwt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorDtoResponse implements Serializable {


    private static final long serialVersionUID = 1L;

    private String error;
    private String message;

    private int status;

    private LocalDateTime date;


    public ResponseEntity<ErrorDtoResponse> toResponseEntity() {
        return ResponseEntity.status(status).body(
                ErrorDtoResponse.builder()
                        .error(error)
                        .message(message)
                        .status(status)
                        .date(date)
                        .build()
        );
    }
}
