package com.iudigital.appspringsecjwt.controller;

import com.iudigital.appspringsecjwt.dto.request.UserDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.UserDtoResponse;
import com.iudigital.appspringsecjwt.service.implement.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<UserDtoResponse>> index() throws NullPointerException{

        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.getAll());
        } catch (NullPointerException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }

    @PostMapping
    public ResponseEntity<UserDtoResponse> store(@Valid @RequestBody UserDtoRequest userDtoRequest) throws IllegalArgumentException{

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(userDtoRequest));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDtoResponse> update(@PathVariable("id") Long id, @Valid @RequestBody UserDtoRequest userDtoRequest) throws NullPointerException, IllegalArgumentException{

        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUser(id, userDtoRequest));
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws NullPointerException{

        try {
            userService.deleteUser(id);

            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
