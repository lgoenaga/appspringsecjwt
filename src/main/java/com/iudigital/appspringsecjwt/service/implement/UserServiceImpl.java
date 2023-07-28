package com.iudigital.appspringsecjwt.service.implement;

import com.iudigital.appspringsecjwt.dto.request.UserDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;
import com.iudigital.appspringsecjwt.dto.response.UserDtoResponse;
import com.iudigital.appspringsecjwt.exception.BadRequestExceptions;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;
import com.iudigital.appspringsecjwt.exception.NullPointerExceptions;
import com.iudigital.appspringsecjwt.model.User;
import com.iudigital.appspringsecjwt.repository.RoleRepository;
import com.iudigital.appspringsecjwt.repository.UserRepository;
import com.iudigital.appspringsecjwt.service.ConstantService;
import com.iudigital.appspringsecjwt.service.interfaces.IUserService;
import com.iudigital.appspringsecjwt.util.VerifyExist;
import com.iudigital.appspringsecjwt.util.VerifyNotExist;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService, UserDetailsService {

    private static final String UPDATE_USER = "User Update";
    private static final String DELETE_USER = "User Delete";
    private static final String SAVE_USER = "User Save";


    final
    UserRepository userRepository;

    final
    RoleRepository roleRepository;

    VerifyExist verifyExist = new VerifyExist();
    VerifyNotExist verifyNotExist = new VerifyNotExist();

    @Override
    public UserDetails loadUserByUsername(String username){
       User user = userRepository.findByUsername(username);

        if(user == null){
            throw new NullPointerException();
        }

        return user;
    }
    @Override
    public UserDtoResponse getUserById(Long id) throws NullPointerExceptions {

        User user = userRepository.findById(id).orElseThrow(
                () -> new NullPointerExceptions(
                        ErrorDtoResponse.builder()
                                .message(ConstantService.NOT_FOUND)
                                .error(ConstantService.NOT_FOUND)
                                .status(HttpStatus.NOT_FOUND.value())
                                .date(LocalDateTime.now())
                                .build()
                )
        );

        return UserDtoResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .enabled(user.getEnabled())
                .roles(user.getRoles())
                .image(user.getImage())
                .dateBirth(user.getDateBirth())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

    }

    @Override
    public List<UserDtoResponse> getAll() throws BadRequestExceptions {

        try {
            List<User> users = userRepository.findAll();

            return users.stream().map(user ->
                    UserDtoResponse.builder()
                            .id(user.getId())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .enabled(user.getEnabled())
                            .roles(user.getRoles())
                            .image(user.getImage())
                            .dateBirth(user.getDateBirth())
                            .createdAt(user.getCreatedAt())
                            .updatedAt(user.getUpdatedAt())
                            .build()
            ).toList();
        } catch (Exception e) {
            throw new BadRequestExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.BAD_REQUEST)
                            .error(ConstantService.BAD_REQUEST)
                            .status(HttpStatus.BAD_REQUEST.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }

    }

    @Override
    public UserDtoResponse saveUser(UserDtoRequest userDtoRequest) throws IllegalArgumentExceptions, BadRequestExceptions {

        boolean userExist = userRepository.existsByUsername(userDtoRequest.getUsername());
        verifyExist.verify(userExist, ConstantService.INFO_FOUND + ConstantService.METHOD + SAVE_USER);

        boolean emailExist = userRepository.existsByEmail(userDtoRequest.getEmail());
        verifyExist.verify(emailExist, ConstantService.INFO_FOUND + ConstantService.METHOD + SAVE_USER);

        try {
            User user = new User();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(userDtoRequest.getPassword());

            user.setFirstName(userDtoRequest.getFirstName());
            user.setLastName(userDtoRequest.getLastName());
            user.setUsername(userDtoRequest.getUsername());
            user.setEmail(userDtoRequest.getEmail());
            user.setPassword(hashedPassword);
            user.setRoles(roleRepository.findByRol("ROLE_USER"));
            user.setImage(userDtoRequest.getImage());
            user.setDateBirth(userDtoRequest.getDateBirth());
            user.setEnabled(true);
            user.setCreatedAt(LocalDate.now());
            user.setUpdatedAt(LocalDate.now());

            user = userRepository.save(user);

            return UserDtoResponse.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .image(user.getImage())
                    .dateBirth(user.getDateBirth())
                    .enabled(user.getEnabled())
                    .roles(user.getRoles())
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    .build();
        } catch (Exception e) {
            throw new BadRequestExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.BAD_REQUEST)
                            .error(ConstantService.BAD_REQUEST)
                            .status(HttpStatus.BAD_REQUEST.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }

    }

    @Override
    public UserDtoResponse updateUser(Long id, UserDtoRequest userDtoRequest) throws NullPointerExceptions, IllegalArgumentExceptions, BadRequestExceptions {

        User userExist = userRepository.findById(id).orElseThrow(
                () -> new NullPointerExceptions(
                        ErrorDtoResponse.builder()
                                .message(ConstantService.NOT_FOUND)
                                .error(ConstantService.NOT_FOUND)
                                .status(HttpStatus.NOT_FOUND.value())
                                .date(LocalDateTime.now())
                                .build()
                )
        );

        if(!userExist.getUsername().equals(userDtoRequest.getUsername())){
            boolean existUserName = userRepository.existsByUsername(userDtoRequest.getUsername());
            verifyExist.verify(existUserName, ConstantService.INFO_FOUND + ConstantService.METHOD + UPDATE_USER);
            userExist.setUsername(userDtoRequest.getUsername());
        }

        if(!userExist.getEmail().equals(userDtoRequest.getEmail())){

            boolean existsByEmail = userRepository.existsByEmail(userDtoRequest.getEmail());
            verifyExist.verify(existsByEmail, ConstantService.INFO_FOUND + ConstantService.METHOD + UPDATE_USER);

            userExist.setEmail(userDtoRequest.getEmail());
        }

        if (userDtoRequest.getRoles() != null && !userDtoRequest.getRoles().isEmpty()) {
            userExist.setRoles(userDtoRequest.getRoles());
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userDtoRequest.getPassword());
        userExist.setPassword(hashedPassword);


        if(userDtoRequest.getEnabled() != null){
            userExist.setEnabled(userDtoRequest.getEnabled());
        }

        if (userDtoRequest.getFirstName() != null) {
            userExist.setFirstName(userDtoRequest.getFirstName());
        }
        if (userDtoRequest.getLastName() != null) {
            userExist.setLastName(userDtoRequest.getLastName());
        }

        if (userDtoRequest.getImage() != null) {
            userExist.setImage(userDtoRequest.getImage());
        }

        if (userDtoRequest.getDateBirth() != null) {
            userExist.setDateBirth(userDtoRequest.getDateBirth());
        }

        userExist.setUpdatedAt(LocalDate.now());

        try {
            userExist = userRepository.save(userExist);

            return UserDtoResponse.builder()
                    .id(userExist.getId())
                    .firstName(userExist.getFirstName())
                    .lastName(userExist.getLastName())
                    .username(userExist.getUsername())
                    .email(userExist.getEmail())
                    .enabled(userExist.getEnabled())
                    .dateBirth(userExist.getDateBirth())
                    .image(userExist.getImage())
                    .roles(userExist.getRoles().stream().toList())
                    .createdAt(userExist.getCreatedAt())
                    .updatedAt(userExist.getUpdatedAt())
                    .build();
        } catch (Exception e) {
            throw new BadRequestExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.BAD_REQUEST)
                            .error(ConstantService.BAD_REQUEST)
                            .status(HttpStatus.BAD_REQUEST.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }
    }
    public void deleteUser(Long id) throws IllegalArgumentExceptions, BadRequestExceptions {

        boolean userExist = userRepository.existsById(id);
        verifyNotExist.verify(userExist, ConstantService.INFO_NOT_FOUND + ConstantService.METHOD + DELETE_USER);


        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new BadRequestExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.BAD_REQUEST)
                            .error(ConstantService.BAD_REQUEST)
                            .status(HttpStatus.BAD_REQUEST.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }

    }

}
