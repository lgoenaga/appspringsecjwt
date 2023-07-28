package com.iudigital.appspringsecjwt.controller;

import com.iudigital.appspringsecjwt.dto.request.CrimeDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CrimeDtoResponse;
import com.iudigital.appspringsecjwt.exception.BadRequestExceptions;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;
import com.iudigital.appspringsecjwt.exception.NullPointerExceptions;
import com.iudigital.appspringsecjwt.service.ConstantService;
import com.iudigital.appspringsecjwt.service.implement.CrimeServiceImpl;
import com.iudigital.appspringsecjwt.util.GeneralExceptions;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/crimes")
@RequiredArgsConstructor
public class CrimeController {
    Logger logger  = Logger.getLogger(CrimeController.class.getName());

    private final CrimeServiceImpl crimeService;

    private String message = "";

    GeneralExceptions generalExceptions = new GeneralExceptions();

    @GetMapping
    public ResponseEntity<Object> index(){
        try {
            List<CrimeDtoResponse> crimeDtoResponses= crimeService.getAll();
            message = ConstantService.MODEL_CRIME + " " + ConstantService.SUCCESSFULLY;
            logger.info(message);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(crimeDtoResponses);
        } catch (BadRequestExceptions e){
            message = ConstantService.BAD_REQUEST + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getErrorDtoResponse()
            );
        } catch (Exception e) {
            return generalExceptions.getConflictException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> show(@PathVariable("id") Long id){
        try {
            CrimeDtoResponse crimeDtoResponse = crimeService.getCrimeById(id);
            message = ConstantService.MODEL_CRIME + " " + ConstantService.SUCCESSFULLY;
            logger.info(message);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(crimeDtoResponse);
        } catch (NullPointerExceptions e) {
            message = ConstantService.NOT_FOUND + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        } catch (Exception e) {
            return generalExceptions.getConflictException(e);
        }
    }

    @PostMapping
    public ResponseEntity<Object> store(@Valid @RequestBody CrimeDtoRequest crimeDtoRequest) {

        try {
            CrimeDtoResponse crimeDtoResponse = crimeService.saveCrime(crimeDtoRequest);
            message = ConstantService.MODEL_CRIME + " " + ConstantService.SUCCESSFULLY;
            logger.info(message);
           return ResponseEntity.status(HttpStatus.ACCEPTED).body(crimeDtoResponse);

        } catch (NullPointerExceptions | IllegalArgumentExceptions e) {
            message = ConstantService.NOT_FOUND + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        } catch (BadRequestExceptions e){
            message = ConstantService.BAD_REQUEST + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getErrorDtoResponse()
            );
        }  catch (Exception e) {
            return generalExceptions.getConflictException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @Valid @RequestBody CrimeDtoRequest crimeDtoRequest) {
        try {
            CrimeDtoResponse crimeDtoResponse = crimeService.updateCrime(id, crimeDtoRequest);
            message = ConstantService.MODEL_CRIME + " " + ConstantService.SUCCESSFULLY;
            logger.info(message);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(crimeDtoResponse);
        } catch (NullPointerExceptions | IllegalArgumentExceptions e) {
            message = ConstantService.NOT_FOUND + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        } catch (BadRequestExceptions e){
            message = ConstantService.BAD_REQUEST + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getErrorDtoResponse()
            );
        } catch (Exception e) {
            return generalExceptions.getConflictException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> destroy(@PathVariable("id") Long id){
        try {
            crimeService.deleteCrime(id);
            message = ConstantService.MODEL_CRIME + " " + ConstantService.SUCCESSFULLY;
            logger.info(message);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
        } catch (IllegalArgumentExceptions e) {
            message = ConstantService.NOT_FOUND + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        }  catch (BadRequestExceptions e){
            message = ConstantService.BAD_REQUEST + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getErrorDtoResponse()
            );
        }catch (Exception e) {
            return generalExceptions.getConflictException(e);
        }
    }

}
