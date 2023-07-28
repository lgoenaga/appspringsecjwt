package com.iudigital.appspringsecjwt.controller;

import com.iudigital.appspringsecjwt.dto.request.UserDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.UserDtoResponse;
import com.iudigital.appspringsecjwt.exception.BadRequestExceptions;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;
import com.iudigital.appspringsecjwt.exception.NullPointerExceptions;
import com.iudigital.appspringsecjwt.service.ConstantService;
import com.iudigital.appspringsecjwt.service.implement.UserServiceImpl;
import com.iudigital.appspringsecjwt.util.GeneralExceptions;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    Logger logger  = Logger.getLogger(UserController.class.getName());

    private final UserServiceImpl userService;

    GeneralExceptions generalExceptions= new GeneralExceptions();

    private String message;

    @GetMapping
    public ResponseEntity<Object> index() {

        try {

            List<UserDtoResponse> userDtoResponseList = userService.getAll();
            message = ConstantService.MODEL_USER + " " + ConstantService.INFO_FOUND;
            logger.info(message);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDtoResponseList);
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

            UserDtoResponse userDtoResponse = userService.getUserById(id);
            message = ConstantService.MODEL_USER + " " + ConstantService.INFO_FOUND;
            logger.info(message);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDtoResponse);
        } catch (NullPointerExceptions e) {
            message = ConstantService.NOT_FOUND + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        } catch (Exception e) {
            return generalExceptions.getGeneralException(e);
        }

    }

    @PostMapping
    public ResponseEntity<Object> store(@Valid @RequestBody UserDtoRequest userDtoRequest){

        try {
            logger.info(ConstantService.MODEL_USER + " " + ConstantService.SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                ConstantService.MODEL_USER + " " + ConstantService.SUCCESSFULLY + " " + "\n" +
                userService.saveUser(userDtoRequest).toString()
            );
        } catch (IllegalArgumentExceptions e) {
            message = ConstantService.NOT_FOUND + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        } catch (BadRequestExceptions e) {
            message = ConstantService.BAD_REQUEST + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getErrorDtoResponse()
            );
        } catch (Exception e) {
            return generalExceptions.getConflictException(e);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @Valid @RequestBody UserDtoRequest userDtoRequest) {

        try {
            UserDtoResponse userDtoResponse = userService.updateUser(id, userDtoRequest);
            message = ConstantService.MODEL_USER + " " + ConstantService.SUCCESSFULLY;
            logger.info(message);
            return  ResponseEntity.status(HttpStatus.ACCEPTED).body(userDtoResponse);
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
            userService.deleteUser(id);
            logger.info(ConstantService.MODEL_USER + " " + ConstantService.SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(ConstantService.MODEL_USER + " " + ConstantService.SUCCESSFULLY);
        } catch (IllegalArgumentExceptions e) {
            message = ConstantService.NOT_FOUND + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    e.getErrorDtoResponse()
            );
        }catch (BadRequestExceptions e) {
            message = ConstantService.BAD_REQUEST + " = " + e.getCause();
            logger.warning(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getErrorDtoResponse()
            );
        } catch (Exception e) {
            return generalExceptions.getConflictException(e);
        }
    }

}
