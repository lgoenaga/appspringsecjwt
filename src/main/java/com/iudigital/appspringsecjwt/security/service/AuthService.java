package com.iudigital.appspringsecjwt.security.service;

import com.iudigital.appspringsecjwt.controller.RolController;
import com.iudigital.appspringsecjwt.model.User;
import com.iudigital.appspringsecjwt.repository.RoleRepository;
import com.iudigital.appspringsecjwt.repository.UserRepository;
import com.iudigital.appspringsecjwt.security.dto.request.LoginRequest;
import com.iudigital.appspringsecjwt.security.dto.request.RegisterRequest;
import com.iudigital.appspringsecjwt.security.dto.response.AuthResponse;
import com.iudigital.appspringsecjwt.security.jwt.JwtService;
import com.iudigital.appspringsecjwt.service.ConstantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public AuthResponse login(LoginRequest request)  {

            Logger logger  = Logger.getLogger(RolController.class.getName());

            boolean existsUser = userRepository.existsByUsername(request.getUsername());
            if (!existsUser) {
                logger.warning(ConstantService.NOT_FOUND + " = " + ConstantService.WRONG_USERNAME);
                return null;
            }
            User user = userRepository.findByUsername(request.getUsername());

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                logger.warning(ConstantService.BAD_REQUEST + " = " + ConstantService.WRONG_PASSWORD);
                return null;
            }
            try {
                return AuthResponse.builder()
                        .token(jwtService.generateToken(user))
                        .build();


            } catch (Exception e) {
                logger.warning(ConstantService.ERROR + " = " + e.getCause());
                return null;
            }
    }

    public String register(RegisterRequest request) {

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roleRepository.findByRol("ROLE_USER"))
                .email(request.getEmail())
                .enabled(true)
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }
}
