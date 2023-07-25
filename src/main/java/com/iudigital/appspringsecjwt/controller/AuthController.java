package com.iudigital.appspringsecjwt.controller;

import com.iudigital.appspringsecjwt.security.dto.response.AuthResponse;
import com.iudigital.appspringsecjwt.security.dto.request.LoginRequest;
import com.iudigital.appspringsecjwt.security.dto.request.RegisterRequest;
import com.iudigital.appspringsecjwt.security.service.AuthService;
import com.iudigital.appspringsecjwt.service.ConstantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    Logger logger  = Logger.getLogger(AuthController.class.getName());

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)throws NullPointerException, IllegalArgumentException {

        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }catch (Exception e) {
            logger.log(Level.SEVERE, String.format("%1$s = %2$s", ConstantService.ERROR, e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {

        return ResponseEntity.ok(authService.register(request));
    }

}
