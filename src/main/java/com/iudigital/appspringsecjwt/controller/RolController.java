package com.iudigital.appspringsecjwt.controller;

import com.iudigital.appspringsecjwt.dto.request.RoleDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.RoleDtoResponse;
import com.iudigital.appspringsecjwt.service.ConstantService;
import com.iudigital.appspringsecjwt.service.implement.RoleServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;


@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RolController{
    Logger logger  = Logger.getLogger(RolController.class.getName());

    private final RoleServiceImpl roleService;

    @GetMapping
    public ResponseEntity<List<RoleDtoResponse>> index() {

        try {
            logger.info(ConstantService.MODEL_ROLE + " " + ConstantService.INFO_FOUND);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(roleService.getAll());
        } catch (Exception e) {
            logger.warning(ConstantService.ERROR + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<RoleDtoResponse> show(@PathVariable("id") Long id) {

        boolean existsRol = roleService.existsRole(id);

        if (!existsRol) {
            logger.warning(ConstantService.NOT_FOUND + " = " + ConstantService.MODEL_ROLE);
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        try {
            RoleDtoResponse roleDtoResponse = roleService.getRolById(id);
            logger.info(ConstantService.MODEL_ROLE + " " + ConstantService.INFO_FOUND);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(roleDtoResponse);
        } catch (Exception e) {
            logger.warning(ConstantService.ERROR + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<RoleDtoResponse> store(@RequestBody RoleDtoRequest roleDtoRequest)  {

        try {
            RoleDtoResponse roleDtoResponse = roleService.saveRole(roleDtoRequest);
            logger.info(ConstantService.MODEL_ROLE + " " + ConstantService.SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.CREATED).body(roleDtoResponse);

        } catch (IllegalArgumentException e) {
            logger.warning(ConstantService.BAD_REQUEST + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } catch (Exception e) {
            logger.warning(ConstantService.ERROR + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<RoleDtoResponse> update(@PathVariable("id") Long id,@Valid @RequestBody RoleDtoRequest roleDtoRequest)  {


        try {
            RoleDtoResponse roleDtoResponse = roleService.updateRole(id, roleDtoRequest);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(roleDtoResponse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> destroy(@PathVariable("id") Long id) {

        boolean existsRol = roleService.existsRole(id);

        if (!existsRol) {
            logger.warning(ConstantService.NOT_FOUND + " = " + ConstantService.MODEL_ROLE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantService.NOT_FOUND + " = " + ConstantService.MODEL_ROLE);
        }

        try {
            roleService.deleteRole(id);
            logger.info(ConstantService.MODEL_ROLE + " " + ConstantService.SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(ConstantService.MODEL_ROLE + " " + ConstantService.SUCCESSFULLY);
        } catch (Exception e) {
            logger.warning(ConstantService.ERROR + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ConstantService.ERROR + " = " + e.getCause());
        }
    }

}
