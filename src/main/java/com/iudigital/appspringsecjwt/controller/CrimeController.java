package com.iudigital.appspringsecjwt.controller;

import com.iudigital.appspringsecjwt.dto.request.CrimeDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;
import com.iudigital.appspringsecjwt.exception.BadRequestExceptions;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;
import com.iudigital.appspringsecjwt.exception.NullPointerExceptions;
import com.iudigital.appspringsecjwt.service.ConstantService;
import com.iudigital.appspringsecjwt.service.implement.CrimeServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/crimes")
@RequiredArgsConstructor
public class CrimeController {
    Logger logger  = Logger.getLogger(CrimeController.class.getName());

    private final CrimeServiceImpl crimeService;

    @GetMapping
    public ResponseEntity<Object> index(){
        try {
            crimeService.getAll();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(ConstantService.MODEL_CRIME + " " + ConstantService.SUCCESSFULLY);
        } catch (BadRequestExceptions e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getErrorDtoResponse()
            );
        } catch (Exception e) {
            logger.log(Level.SEVERE, MessageFormat.format("{0} = {1}", ConstantService.ERROR, e.getMessage()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    ErrorDtoResponse.builder()
                            .error(ConstantService.VIOLATION_CONSTRAINT)
                            .message(e.getMessage())
                            .status(HttpStatus.CONFLICT.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> show(@PathVariable("id") Long id){
        try {
            crimeService.getCrimeById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(ConstantService.MODEL_CRIME + " " + ConstantService.SUCCESSFULLY);
        } catch (NullPointerExceptions e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        } catch (Exception e) {
            logger.log(Level.SEVERE, MessageFormat.format("{0} = {1}", ConstantService.ERROR, e.getMessage()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    ErrorDtoResponse.builder()
                            .error(ConstantService.VIOLATION_CONSTRAINT)
                            .message(e.getMessage())
                            .status(HttpStatus.CONFLICT.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }
    }

    @PostMapping
    public ResponseEntity<Object> store(@Valid @RequestBody CrimeDtoRequest crimeDtoRequest) {

        try {
           crimeService.saveCrime(crimeDtoRequest);
           return ResponseEntity.status(HttpStatus.ACCEPTED).body(ConstantService.MODEL_CRIME + " " + ConstantService.SUCCESSFULLY);

        } catch (NullPointerExceptions | IllegalArgumentExceptions e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        } catch (BadRequestExceptions e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getErrorDtoResponse()
            );
        }  catch (Exception e) {
            logger.log(Level.SEVERE, MessageFormat.format("{0} = {1}", ConstantService.ERROR, e.getMessage()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    ErrorDtoResponse.builder()
                            .error(ConstantService.VIOLATION_CONSTRAINT)
                            .message(e.getMessage())
                            .status(HttpStatus.CONFLICT.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @Valid @RequestBody CrimeDtoRequest crimeDtoRequest) {
        try {
            crimeService.updateCrime(id, crimeDtoRequest);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(ConstantService.MODEL_CRIME + " " + ConstantService.SUCCESSFULLY);
        } catch (NullPointerExceptions | IllegalArgumentExceptions e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        } catch (BadRequestExceptions e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getErrorDtoResponse()
            );
        } catch (Exception e) {
            logger.log(Level.SEVERE, MessageFormat.format("{0} = {1}", ConstantService.ERROR, e.getMessage()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    ErrorDtoResponse.builder()
                            .error(ConstantService.VIOLATION_CONSTRAINT)
                            .message(e.getMessage())
                            .status(HttpStatus.CONFLICT.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> destroy(@PathVariable("id") Long id){
        try {
            crimeService.deleteCrime(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(ConstantService.MODEL_CRIME + " " + ConstantService.SUCCESSFULLY);
        } catch (IllegalArgumentExceptions e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        }  catch (BadRequestExceptions e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getErrorDtoResponse()
            );
        }catch (Exception e) {
            logger.log(Level.SEVERE, MessageFormat.format("{0} = {1}", ConstantService.ERROR, e.getMessage()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    ErrorDtoResponse.builder()
                            .error(ConstantService.VIOLATION_CONSTRAINT)
                            .message(e.getMessage())
                            .status(HttpStatus.CONFLICT.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }
    }

}
