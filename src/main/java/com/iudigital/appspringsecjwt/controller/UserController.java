package com.iudigital.appspringsecjwt.controller;

import com.iudigital.appspringsecjwt.dto.request.UserDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.UserDtoResponse;
import com.iudigital.appspringsecjwt.service.ConstantService;
import com.iudigital.appspringsecjwt.service.implement.UserServiceImpl;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    Logger logger  = Logger.getLogger(UserController.class.getName());

    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<UserDtoResponse>> index() throws NullPointerException{

        try {
            logger.info(ConstantService.MODEL_USER + " " + ConstantService.INFO_FOUND);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.getAll());
        } catch (NullPointerException e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("%1s = %2s", ConstantService.ERROR, e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDtoResponse> show(@PathVariable("id") Long id) throws NullPointerException{
        try {
            logger.info(ConstantService.MODEL_USER + " " + ConstantService.INFO_FOUND);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.getUserById(id));
        } catch (NullPointerException e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("%s = %s", ConstantService.ERROR, e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping
    public ResponseEntity<String> store(@Valid @RequestBody UserDtoRequest userDtoRequest) throws IllegalArgumentException{

        try {
            logger.info(ConstantService.MODEL_USER + " " + ConstantService.SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                ConstantService.MODEL_USER + " " + ConstantService.SUCCESSFULLY + " " + "\n" +
                userService.saveUser(userDtoRequest).toString()
            );
        } catch (IllegalArgumentException e) {
            logger.warning(ConstantService.BAD_REQUEST + " = " + e.getCause());
            return ResponseEntity.badRequest().body(ConstantService.MODEL_USER + " " + ConstantService.BAD_REQUEST);
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("%1$s = %2$s", ConstantService.ERROR, e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ConstantService.ERROR + " = " + e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @Valid @RequestBody UserDtoRequest userDtoRequest) throws NullPointerException, IllegalArgumentException, EntityNotFoundException {

        try {
            logger.info(ConstantService.MODEL_USER + " " + ConstantService.SUCCESSFULLY);

            return  ResponseEntity.status(HttpStatus.ACCEPTED).body(
                    ConstantService.MODEL_USER + " " + ConstantService.SUCCESSFULLY + " " + "\n" +
                            userService.updateUser(id, userDtoRequest).toString()
            );
        } catch (NullPointerException e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantService.MODEL_USER + " " + ConstantService.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            logger.warning(ConstantService.BAD_REQUEST + " = " + e.getCause());
            return ResponseEntity.badRequest().body(ConstantService.MODEL_USER + " " + ConstantService.BAD_REQUEST);
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("%s = %s", ConstantService.ERROR, e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ConstantService.ERROR + " = " + e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) throws NullPointerException{

        try {
            userService.deleteUser(id);
            logger.info(ConstantService.MODEL_USER + " " + ConstantService.SUCCESSFULLY);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(ConstantService.MODEL_USER + " " + ConstantService.SUCCESSFULLY);
        } catch (NullPointerException | EntityNotFoundException e) {
            logger.warning(ConstantService.NOT_FOUND + " = " + e.getCause());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantService.MODEL_USER + " " + ConstantService.NOT_FOUND);
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("%1$s = %2$s", ConstantService.ERROR, e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ConstantService.ERROR + " = " + e.getMessage());
        }
    }

}
