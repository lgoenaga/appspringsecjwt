package com.iudigital.appspringsecjwt.util;

import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;
import com.iudigital.appspringsecjwt.service.ConstantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneralExceptions {

    Logger logger  = Logger.getLogger(GeneralExceptions.class.getName());
    String message = "";
    public ResponseEntity<Object> getGeneralException(Exception e){
        message += ConstantService.ERROR + " = " + e.getMessage();
        logger.log(Level.SEVERE, message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorDtoResponse.builder()
                        .error(ConstantService.ERROR)
                        .message(e.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .date(LocalDateTime.now())
                        .build()
        );
    }
    public ResponseEntity<Object> getConflictException(Exception e){
        message += ConstantService.ILLEGAL_ARGUMENT + " = " + e.getMessage();
        logger.log(Level.SEVERE, message);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ErrorDtoResponse.builder()
                        .error(ConstantService.VIOLATION_CONSTRAINT)
                        .message(e.getMessage())
                        .status(HttpStatus.CONFLICT.value())
                        .date(LocalDateTime.now())
                        .build()
        );
    }

    public ResponseEntity<Object> getBadRequestException(Exception e){
        message += ConstantService.BAD_REQUEST + " = " + e.getMessage();
        logger.log(Level.SEVERE, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorDtoResponse.builder()
                        .error(ConstantService.BAD_REQUEST)
                        .message(e.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .date(LocalDateTime.now())
                        .build()
        );
    }

}
