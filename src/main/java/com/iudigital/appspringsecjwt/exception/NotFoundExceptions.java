package com.iudigital.appspringsecjwt.exception;

import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;

public class NotFoundExceptions extends RestExceptions{

    private static final long serialVersionUID = 1L;

    public NotFoundExceptions() {
        super();
    }

    public NotFoundExceptions(String message) {
        super(message);
    }

    public NotFoundExceptions(ErrorDtoResponse errorDtoResponse) {
        super(errorDtoResponse);
    }

}
