package com.iudigital.appspringsecjwt.service.interfaces;

import com.iudigital.appspringsecjwt.dto.request.UserRequest;

import com.iudigital.appspringsecjwt.dto.response.UserResponse;
import com.iudigital.appspringsecjwt.model.User;
import org.springframework.stereotype.Component;

@Component
public interface IUserService {

    User findByUsername(String username);
}
