package com.iudigital.appspringsecjwt.controller;

import com.iudigital.appspringsecjwt.dto.request.CaseDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CaseDtoResponse;
import com.iudigital.appspringsecjwt.service.ConstantService;
import com.iudigital.appspringsecjwt.service.implement.CaseServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/cases")
@RequiredArgsConstructor
public class CaseController {

    Logger logger  = Logger.getLogger(CaseController.class.getName());

    final
    CaseServiceImpl caseService;


    @GetMapping
    public ResponseEntity<List<CaseDtoResponse>> index() throws NullPointerException{
        try {
            logger.info(ConstantService.MODEL_CASE + " " + ConstantService.INFO_FOUND);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(caseService.getAll());
        } catch (NullPointerException e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.warning(ConstantService.ERROR + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaseDtoResponse> show(@PathVariable("id") Long id) throws NullPointerException{
        try {
            logger.info(ConstantService.MODEL_CASE + " " + ConstantService.INFO_FOUND);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(caseService.getCaseById(id));
        } catch (NullPointerException e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.warning(ConstantService.ERROR + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> store(@Valid @RequestBody CaseDtoRequest caseDtoRequest)throws IllegalArgumentException, NullPointerException{
        try {
            logger.info(ConstantService.MODEL_CASE + " " + ConstantService.SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ConstantService.MODEL_CASE + " " + ConstantService.SUCCESSFULLY + " " + "\n" + caseService.saveCase(caseDtoRequest).toString()
            );
        } catch (IllegalArgumentException e) {
            logger.warning(ConstantService.BAD_REQUEST + " = " + e.getCause());
            return ResponseEntity.badRequest().body(ConstantService.MODEL_CASE+ " " + ConstantService.BAD_REQUEST);
        }  catch (NullPointerException e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.warning(ConstantService.ERROR + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @Valid @RequestBody CaseDtoRequest caseDtoRequest) throws IllegalArgumentException, NullPointerException{
        try {
            logger.info(ConstantService.MODEL_CASE + " " + ConstantService.SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                    ConstantService.MODEL_CASE + " " + ConstantService.SUCCESSFULLY + " " + "\n" +
                            caseService.updateCase(id, caseDtoRequest).toString()
            );
        } catch (IllegalArgumentException e) {
            logger.warning(ConstantService.BAD_REQUEST + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ConstantService.MODEL_CASE + " " + ConstantService.BAD_REQUEST);
        }  catch (NullPointerException e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantService.MODEL_CASE + " " + ConstantService.NOT_FOUND);
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("%1$s = %2$s", ConstantService.ERROR, e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ConstantService.ERROR + " = " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> destroy(@PathVariable("id") Long id) throws NullPointerException{
        try {
            caseService.deleteCase(id);
            logger.info(ConstantService.MODEL_CASE + " " + ConstantService.SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(ConstantService.MODEL_CASE + " " + ConstantService.SUCCESSFULLY);
        } catch (NullPointerException | EntityNotFoundException e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantService.MODEL_CASE + " " + ConstantService.NOT_FOUND);
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("%1$s = %2$s", ConstantService.ERROR, e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ConstantService.ERROR + " = " + e.getMessage());
        }
    }

}
