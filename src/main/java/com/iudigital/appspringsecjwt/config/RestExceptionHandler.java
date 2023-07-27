package com.iudigital.appspringsecjwt.config;

import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;
import com.iudigital.appspringsecjwt.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.logging.Logger;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    private static final Logger log = Logger.getLogger(RestExceptionHandler.class.getName());


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestExceptions.class})
    public ResponseEntity<ErrorDtoResponse> getBadRequest(BadRequestExceptions e) {
        log.warning("Bad request: " + e.getMessage());
        return new ResponseEntity<>(e.getErrorDtoResponse(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({RestExceptions.class})
    public ResponseEntity<ErrorDtoResponse> getInternalServerError(RestExceptions e) {
        log.warning("Internal server error: " + e.getMessage());
        return new ResponseEntity<>(e.getErrorDtoResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NullPointerExceptions.class})
    public ResponseEntity<ErrorDtoResponse> getNullPointer(NullPointerExceptions e) {
        log.warning("Null pointer: " + e.getMessage());
        return new ResponseEntity<>(e.getErrorDtoResponse(), HttpStatus.NOT_FOUND);

    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({IllegalArgumentExceptions.class})
    public ResponseEntity<ErrorDtoResponse> getIllegalArgument(IllegalArgumentExceptions e) {
        log.warning("Illegal argument: " + e.getMessage());
        return new ResponseEntity<>(e.getErrorDtoResponse(), HttpStatus.CONFLICT);

    }

}
