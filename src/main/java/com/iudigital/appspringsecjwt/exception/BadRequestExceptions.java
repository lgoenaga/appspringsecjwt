package com.iudigital.appspringsecjwt.exception;

import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;

import java.io.Serial;

public class BadRequestExceptions extends RestExceptions{

    @Serial
    private static final long serialVersionUID = 1L;

    public BadRequestExceptions() {
        super();
    }

    public BadRequestExceptions(ErrorDtoResponse errorDtoResponse) {
        super(errorDtoResponse);
    }

    public BadRequestExceptions(String message) {
        super(message);
    }

}
