package com.iudigital.appspringsecjwt.util;

import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;
import com.iudigital.appspringsecjwt.service.ConstantService;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import static java.text.MessageFormat.format;

public class VerifyExist {

    private static final Logger logger  = Logger.getLogger(VerifyExist.class.getName());

    public void verify(boolean isExist, String message) throws IllegalArgumentExceptions {

        if(isExist){
            logger.warning(format("{0} = {1}", ConstantService.ILLEGAL_ARGUMENT, message));
            throw new IllegalArgumentExceptions(
                    ErrorDtoResponse.builder()
                            .message(message)
                            .error(HttpStatus.CONFLICT.getReasonPhrase())
                            .status(HttpStatus.CONFLICT.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }
    }
}
