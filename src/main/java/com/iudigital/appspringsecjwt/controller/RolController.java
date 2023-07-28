package com.iudigital.appspringsecjwt.controller;

import com.iudigital.appspringsecjwt.dto.request.RoleDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;
import com.iudigital.appspringsecjwt.dto.response.RoleDtoResponse;
import com.iudigital.appspringsecjwt.exception.BadRequestExceptions;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;
import com.iudigital.appspringsecjwt.exception.NullPointerExceptions;
import com.iudigital.appspringsecjwt.service.ConstantService;
import com.iudigital.appspringsecjwt.service.implement.RoleServiceImpl;
import com.iudigital.appspringsecjwt.util.GeneralExceptions;
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

    private String message="";

    GeneralExceptions generalExceptions = new GeneralExceptions();

    @GetMapping
    public ResponseEntity<Object> index() {

        try {
            List<RoleDtoResponse> roles = roleService.getAll();

            if (!roles.isEmpty()) {
                message = ConstantService.MODEL_ROLE + " " + ConstantService.INFO_FOUND;
                logger.info(message);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(roleService.getAll());
            }else {
                message = ConstantService.NOT_FOUND + " = " + ConstantService.METHOD + ConstantService.MODEL_ROLE;
                logger.warning(message);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                        ErrorDtoResponse.builder()
                                .message(message)
                                .error(HttpStatus.NO_CONTENT.getReasonPhrase())
                                .status(HttpStatus.NO_CONTENT.value())
                                .date(LocalDateTime.now())
                                .build()
                );
            }
        }catch (BadRequestExceptions e){
            message = ConstantService.BAD_REQUEST + " = " + ConstantService.METHOD + ConstantService.MODEL_ROLE;
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getErrorDtoResponse()
            );

        } catch (Exception e) {
            return generalExceptions.getGeneralException(e);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> show(@PathVariable("id") Long id) {

        try {
            RoleDtoResponse roleDtoResponse = roleService.getRolById(id);
            message = ConstantService.MODEL_ROLE + " " + ConstantService.SUCCESSFULLY;
            logger.info(message);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(roleDtoResponse);
        } catch (IllegalArgumentExceptions | NullPointerExceptions e) {
            message = ConstantService.NOT_FOUND + " = " + ConstantService.METHOD + ConstantService.MODEL_ROLE;
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        }catch (Exception e) {
            return generalExceptions.getGeneralException(e);
        }

    }

    @PostMapping
    public ResponseEntity<Object> store(@Valid @RequestBody RoleDtoRequest roleDtoRequest)  {

        try {
            RoleDtoResponse roleDtoResponse = roleService.saveRole(roleDtoRequest);
            if (roleDtoResponse!=null){
                message = ConstantService.MODEL_ROLE + " " + ConstantService.SUCCESSFULLY;
                logger.info(message);
                return ResponseEntity.status(HttpStatus.CREATED).body(roleDtoResponse);
            }else {
                message = ConstantService.BAD_REQUEST + " = " + ConstantService.METHOD + ConstantService.MODEL_ROLE;
                logger.warning(message);
                return ResponseEntity.badRequest().body(
                            ErrorDtoResponse.builder()
                                .error(ConstantService.BAD_REQUEST)
                                .message(ConstantService.BAD_REQUEST)
                                .status(HttpStatus.BAD_REQUEST.value())
                                .date(LocalDateTime.now())
                                .build() );
            }
        } catch (IllegalArgumentExceptions e) {
            message = ConstantService.BAD_REQUEST + " = " + ConstantService.METHOD + ConstantService.MODEL_ROLE;
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    e.getErrorDtoResponse()
            );
        } catch (Exception e) {
           return generalExceptions.getGeneralException(e);
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @Valid @RequestBody RoleDtoRequest roleDtoRequest)  {

        try {
            RoleDtoResponse roleDtoResponse = roleService.updateRole(id, roleDtoRequest);
            message = ConstantService.MODEL_ROLE + " " + ConstantService.SUCCESSFULLY;
            logger.info(message);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(roleDtoResponse);
        } catch (IllegalArgumentExceptions | NullPointerExceptions e){
            message = ConstantService.NOT_FOUND + " = " + ConstantService.METHOD + ConstantService.MODEL_ROLE;
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        } catch (Exception e) {
            return generalExceptions.getGeneralException(e);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> destroy(@PathVariable("id") @Valid Long id) {

        try {
            message="";
            roleService.deleteRole(id);
            message += ConstantService.MODEL_ROLE + " " + ConstantService.SUCCESSFULLY;
            logger.info(message);
            message = "";
            message += ConstantService.MODEL_ROLE + " " + ConstantService.SUCCESSFULLY;
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
        } catch (IllegalArgumentExceptions e){
            message = ConstantService.NOT_FOUND + " = " + ConstantService.METHOD + ConstantService.MODEL_ROLE;
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                 e.getErrorDtoResponse()
            );
        } catch (Exception e) {
            return generalExceptions.getConflictException(e);
        }
    }

}
