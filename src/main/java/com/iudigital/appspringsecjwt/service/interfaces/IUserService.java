package com.iudigital.appspringsecjwt.service.interfaces;

import com.iudigital.appspringsecjwt.dto.request.UserDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.UserDtoResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IUserService {

    UserDtoResponse getUserById(Long id);

    List<UserDtoResponse> getAll();

    UserDtoResponse saveUser(UserDtoRequest userDtoRequest);

    UserDtoResponse updateUser(Long id, UserDtoRequest userDtoRequest);

    void deleteUser(Long id);

}
