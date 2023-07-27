package com.iudigital.appspringsecjwt.controller;

import com.iudigital.appspringsecjwt.dto.request.RoleDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;
import com.iudigital.appspringsecjwt.dto.response.RoleDtoResponse;
import com.iudigital.appspringsecjwt.exception.BadRequestExceptions;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;
import com.iudigital.appspringsecjwt.exception.NullPointerExceptions;
import com.iudigital.appspringsecjwt.service.ConstantService;
import com.iudigital.appspringsecjwt.service.implement.RoleServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public ResponseEntity<Object> show(@PathVariable("id") Long id) {

        try {

            RoleDtoResponse roleDtoResponse = roleService.getRolById(id);
            logger.info(ConstantService.MODEL_ROLE + " " + ConstantService.SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(roleDtoResponse);
        } catch (IllegalArgumentExceptions | NullPointerExceptions e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getErrorDtoResponse()
            );
        }

    }

    @PostMapping
    public ResponseEntity<Object> store(@Valid @RequestBody RoleDtoRequest roleDtoRequest)  {

        try {
            RoleDtoResponse roleDtoResponse = roleService.saveRole(roleDtoRequest);
            logger.info(ConstantService.MODEL_ROLE + " " + ConstantService.SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.CREATED).body(roleDtoResponse);
        } catch (BadRequestExceptions e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getErrorDtoResponse()
            );
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @Valid @RequestBody RoleDtoRequest roleDtoRequest)  {

        try {
            RoleDtoResponse roleDtoResponse = roleService.updateRole(id, roleDtoRequest);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(roleDtoResponse);
        } catch (IllegalArgumentExceptions | NullPointerExceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        } catch (BadRequestExceptions e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getErrorDtoResponse()
            );
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> destroy(@PathVariable("id") @Valid Long id) {

        try {
            roleService.deleteRole(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(ConstantService.MODEL_ROLE + " " + ConstantService.SUCCESSFULLY);
        } catch (IllegalArgumentExceptions e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                 e.getErrorDtoResponse()
            );
        } catch (Exception e) {
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
