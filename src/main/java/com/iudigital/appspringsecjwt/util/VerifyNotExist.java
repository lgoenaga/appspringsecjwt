package com.iudigital.appspringsecjwt.util;

import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;
import com.iudigital.appspringsecjwt.service.ConstantService;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class VerifyNotExist {

    private static final Logger logger  = Logger.getLogger(VerifyNotExist.class.getName());

    public void verify(boolean isExist, String message) throws IllegalArgumentExceptions {

        if(!isExist){
            logger.warning(ConstantService.ILLEGAL_ARGUMENT + " = " + message);
            throw new IllegalArgumentExceptions(
                    ErrorDtoResponse.builder()
                            .message(message)
                            .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                            .status(HttpStatus.NOT_FOUND.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }
    }
}
