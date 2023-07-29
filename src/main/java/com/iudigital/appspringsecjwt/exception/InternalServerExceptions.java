package com.iudigital.appspringsecjwt.exception;

import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;

import java.io.Serial;

public class InternalServerExceptions extends RestExceptions{

    @Serial
    private static final long serialVersionUID = 1L;

    private String codeError;

    public InternalServerExceptions(String message, String codeError, Exception ex) {
        super(message, ex);
        this.codeError = codeError;
    }

    public InternalServerExceptions(String message, Exception ex) {
        super(message, ex);
    }

    public InternalServerExceptions() {
        super();
    }

    public InternalServerExceptions(ErrorDtoResponse errorDtoResponse) {
        super(errorDtoResponse);
    }

    public String getCodeError() {
        return codeError;
    }

}
