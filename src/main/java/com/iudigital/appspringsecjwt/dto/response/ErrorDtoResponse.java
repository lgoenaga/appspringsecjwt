package com.iudigital.appspringsecjwt.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorDtoResponse implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    private String error;
    private String message;

    private int status;

    private LocalDateTime date;

    @Override
    public String toString() {
        return "{\n" +
                "\t error = " + error + '\n' +
                "\t message = " + message + '\n' +
                "\t status = " + status + '\n' +
                "\t date = " + date +  '\n' +
                "}";
    }
}
