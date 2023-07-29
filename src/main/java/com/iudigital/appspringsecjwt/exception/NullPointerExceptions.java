package com.iudigital.appspringsecjwt.exception;

import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;

import java.io.Serial;

public class NullPointerExceptions extends RestExceptions{

    @Serial
    private static final long serialVersionUID = 1L;

    public NullPointerExceptions() {
        super();
    }

    public NullPointerExceptions(ErrorDtoResponse errorDtoResponse) {
        super(errorDtoResponse);
    }

    public NullPointerExceptions(String message) {
        super(message);
    }

}
