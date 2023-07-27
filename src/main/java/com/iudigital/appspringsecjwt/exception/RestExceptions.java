package com.iudigital.appspringsecjwt.exception;

import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;

import java.io.Serial;

public class RestExceptions extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    private ErrorDtoResponse errorDtoResponse;

    public RestExceptions(ErrorDtoResponse errorDtoResponse) {
        super(errorDtoResponse.getError());
        this.errorDtoResponse = errorDtoResponse;
    }

    public RestExceptions () {
        super();
    }

    public RestExceptions (String message) {
        super(message);
    }

    public RestExceptions (String message, Exception ex) {
        super(message, ex);
    }


    public ErrorDtoResponse getErrorDtoResponse() {
        return errorDtoResponse;
    }

}
