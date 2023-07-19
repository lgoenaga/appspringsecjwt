package com.iudigital.appspringsecjwt.service.implement;

import com.iudigital.appspringsecjwt.dto.request.UserRequest;
import com.iudigital.appspringsecjwt.model.User;
import com.iudigital.appspringsecjwt.repository.UserRepository;
import com.iudigital.appspringsecjwt.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor

public class UserServiceImpl implements IUserService, UserDetailsService {

    final
    UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
