package com.iudigital.appspringsecjwt.service.implement;

import com.iudigital.appspringsecjwt.dto.request.UserDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.UserDtoResponse;
import com.iudigital.appspringsecjwt.model.User;
import com.iudigital.appspringsecjwt.repository.RoleRepository;
import com.iudigital.appspringsecjwt.repository.UserRepository;
import com.iudigital.appspringsecjwt.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor

public class UserServiceImpl implements IUserService, UserDetailsService {

    final
    UserRepository userRepository;

    final
    RoleRepository roleRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<UserDtoResponse> getAll() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) throw new NullPointerException();

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

    }

    @Transactional
    public UserDtoResponse addUser(UserDtoRequest userDtoRequest){

        boolean userExist =  userRepository.existsByUsername(userDtoRequest.getUsername());
        boolean emailExist = userRepository.existsByEmail(userDtoRequest.getEmail());

        if(userExist || emailExist){

            throw new IllegalArgumentException();
        }

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

    }


    @Transactional
    public UserDtoResponse updateUser(Long id, UserDtoRequest userDtoRequest) {

        User userExist = userRepository.findById(id).orElse(null);

        if(userExist == null){
            throw new NullPointerException();
        }

        if(userExist.getRoles().isEmpty()){
            userExist.setRoles(roleRepository.findByRol("ROLE_USER"));
        }

        if(!userExist.getUsername().equals(userDtoRequest.getUsername())){
            boolean existUserName = userRepository.existsByUsername(userDtoRequest.getUsername());
            if(existUserName){
                throw new IllegalArgumentException();
            }

            userExist.setUsername(userDtoRequest.getUsername());
        }

        if(!userExist.getEmail().equals(userDtoRequest.getEmail())){

            boolean existsByEmail = userRepository.existsByEmail(userDtoRequest.getEmail());

            if(existsByEmail){
                throw new IllegalArgumentException();
            }
            userExist.setEmail(userDtoRequest.getEmail());
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

        if (userDtoRequest.getRoles() != null) {
            userExist.setRoles(userDtoRequest.getRoles());
        }

        userExist.setUpdatedAt(LocalDate.now());

        return UserDtoResponse.builder()
                .id(userExist.getId())
                .firstName(userExist.getFirstName())
                .lastName(userExist.getLastName())
                .username(userExist.getUsername())
                .email(userExist.getEmail())
                .enabled(userExist.getEnabled())
                .dateBirth(userExist.getDateBirth())
                .image(userExist.getImage())
                .roles(userExist.getRoles())
                .createdAt(userExist.getCreatedAt())
                .updatedAt(userExist.getUpdatedAt())
                .build();
    }
    public void deleteUser(Long id) {

        userRepository.deleteById(id);

    }


}
