package com.iudigital.appspringsecjwt.exception;

import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;

import java.io.Serial;

public class IllegalArgumentExceptions extends RestExceptions{

    @Serial
    private static final long serialVersionUID = 1L;

    public IllegalArgumentExceptions() {
        super();
    }

    public IllegalArgumentExceptions(ErrorDtoResponse errorDtoResponse) {
        super(errorDtoResponse);
    }

    public IllegalArgumentExceptions(String message) {
        super(message);
    }
}
