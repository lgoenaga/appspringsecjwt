package com.iudigital.appspringsecjwt.service.interfaces;

import com.iudigital.appspringsecjwt.dto.request.UserDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.UserDtoResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public interface IUserService {

    @Transactional(readOnly = true)
    UserDtoResponse getUserById(Long id);

    @Transactional(readOnly = true)
    List<UserDtoResponse> getAll();

    @Transactional
    UserDtoResponse saveUser(UserDtoRequest userDtoRequest);

    @Transactional
    UserDtoResponse updateUser(Long id, UserDtoRequest userDtoRequest);

    @Transactional
    void deleteUser(Long id);

}
