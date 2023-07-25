package com.iudigital.appspringsecjwt.exception;

import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;

public class NullPointerExceptions extends RestExceptions{

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
