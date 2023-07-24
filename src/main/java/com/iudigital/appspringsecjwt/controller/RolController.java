package com.iudigital.appspringsecjwt.controller;

import com.iudigital.appspringsecjwt.dto.request.RoleDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.RoleDtoResponse;
import com.iudigital.appspringsecjwt.service.ConstantService;
import com.iudigital.appspringsecjwt.service.implement.RoleServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RolController{
    Logger logger  = Logger.getLogger(RolController.class.getName());

    private final RoleServiceImpl roleService;

    @GetMapping
    public ResponseEntity<List<RoleDtoResponse>> index() throws NullPointerException{

        try {
            logger.info(ConstantService.MODEL_ROLE + " " + ConstantService.INFO_FOUND);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(roleService.getAll());
        } catch (NullPointerException e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.warning(ConstantService.ERROR + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<RoleDtoResponse> show(@PathVariable("id") Long id) throws NullPointerException{

        try {
            logger.info(ConstantService.MODEL_ROLE + " " + ConstantService.INFO_FOUND);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(roleService.getRolById(id));
        } catch (NullPointerException e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.warning(ConstantService.ERROR + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> store(@RequestBody RoleDtoRequest roleDtoRequest) throws IllegalArgumentException {

        try {
            logger.info(ConstantService.MODEL_ROLE + " " + ConstantService.SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ConstantService.MODEL_ROLE + " " + ConstantService.SUCCESSFULLY + " " + "\n" +
                            roleService.saveRole(roleDtoRequest).toString()
            );
        } catch (IllegalArgumentException e) {
            logger.warning(ConstantService.BAD_REQUEST + " = " + e.getCause());
            return ResponseEntity.badRequest().body(ConstantService.MODEL_ROLE + " " + ConstantService.BAD_REQUEST);
        } catch (Exception e) {
            logger.warning(ConstantService.ERROR + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody RoleDtoRequest roleDtoRequest) throws IllegalArgumentException {

        try {
            logger.info(ConstantService.MODEL_ROLE + " " + ConstantService.SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ConstantService.MODEL_ROLE + " " + ConstantService.SUCCESSFULLY + " " + "\n" +
                            roleService.updateRole(id, roleDtoRequest).toString()
            );
        } catch (IllegalArgumentException e) {
            logger.warning(ConstantService.BAD_REQUEST + " = " + e.getCause());
            return ResponseEntity.badRequest().body(ConstantService.MODEL_ROLE + " " + ConstantService.BAD_REQUEST);
        } catch (Exception e) {
            logger.warning(ConstantService.ERROR + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> destroy(@PathVariable("id") Long id) throws NullPointerException{

        try {
            roleService.deleteRole(id);
            logger.info(ConstantService.MODEL_ROLE + " " + ConstantService.SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(ConstantService.MODEL_ROLE + " " + ConstantService.SUCCESSFULLY);
        } catch (NullPointerException | EntityNotFoundException e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantService.MODEL_ROLE + " " + ConstantService.NOT_FOUND);
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("%1$s = %2$s", ConstantService.ERROR, e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ConstantService.ERROR + " = " + e.getMessage());
        }
    }
}
