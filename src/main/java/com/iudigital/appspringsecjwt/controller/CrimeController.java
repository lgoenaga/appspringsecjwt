package com.iudigital.appspringsecjwt.controller;

import com.iudigital.appspringsecjwt.dto.request.CrimeDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CrimeDtoResponse;
import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;
import com.iudigital.appspringsecjwt.service.ConstantService;
import com.iudigital.appspringsecjwt.service.implement.CrimeServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/crimes")
@RequiredArgsConstructor
public class CrimeController {
    Logger logger  = Logger.getLogger(CrimeController.class.getName());

    private final CrimeServiceImpl crimeService;

    @GetMapping
    public ResponseEntity<List<CrimeDtoResponse>> index() throws NullPointerException{
        try {
            logger.info(ConstantService.MODEL_CRIME + " " + ConstantService.INFO_FOUND);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(crimeService.getAll());
        } catch (NullPointerException e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.warning(ConstantService.ERROR + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CrimeDtoResponse> show(@PathVariable("id") Long id) throws NullPointerException{
        try {
            logger.info(ConstantService.MODEL_CRIME + " " + ConstantService.INFO_FOUND);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(crimeService.getCrimeById(id));
        } catch (NullPointerException e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.warning(ConstantService.ERROR + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> store(@Valid @RequestBody CrimeDtoRequest crimeDtoRequest) throws IllegalArgumentException, NullPointerException{

        try {
            logger.info(ConstantService.MODEL_CRIME + " " + ConstantService.SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ConstantService.MODEL_CRIME + " " + ConstantService.SUCCESSFULLY + " " + "\n" + crimeService.saveCrime(crimeDtoRequest).toString()
            );
        } catch (IllegalArgumentException e) {
            logger.warning(ConstantService.BAD_REQUEST + " = " + e.getCause());
            return ResponseEntity.badRequest().body(ConstantService.MODEL_CRIME+ " " + ConstantService.BAD_REQUEST);
        }  catch (NullPointerException e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.warning(ConstantService.ERROR + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @Valid @RequestBody CrimeDtoRequest crimeDtoRequest) throws IllegalArgumentException, NullPointerException{
        try {
            logger.info(ConstantService.MODEL_CRIME + " " + ConstantService.SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                    ConstantService.MODEL_CRIME + " " + ConstantService.SUCCESSFULLY + " " + "\n" +
                            crimeService.updateCrime(id, crimeDtoRequest).toString()
            );
        } catch (IllegalArgumentException e) {
            logger.warning(ConstantService.BAD_REQUEST + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConstantService.MODEL_CRIME + " " + ConstantService.BAD_REQUEST);
        }  catch (NullPointerException e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantService.MODEL_CRIME + " " + ConstantService.NOT_FOUND);
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("%1$s = %2$s", ConstantService.ERROR, e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ConstantService.ERROR + " = " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> destroy(@PathVariable("id") Long id) throws NullPointerException{
        try {
            crimeService.deleteCrime(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(ConstantService.MODEL_CRIME + " " + ConstantService.SUCCESSFULLY);
        } catch (IllegalArgumentExceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        }catch (Exception e) {
            logger.log(Level.SEVERE, String.format("%1$s = %2$s", ConstantService.ERROR, e.getMessage()));
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
