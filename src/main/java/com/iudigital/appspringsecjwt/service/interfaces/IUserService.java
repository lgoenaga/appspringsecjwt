package com.iudigital.appspringsecjwt.service.interfaces;

import com.iudigital.appspringsecjwt.dto.request.UserDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.UserDtoResponse;
import com.iudigital.appspringsecjwt.exception.BadRequestExceptions;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;
import com.iudigital.appspringsecjwt.exception.NullPointerExceptions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public interface IUserService {

    @Transactional(readOnly = true)
    UserDtoResponse getUserById(Long id) throws NullPointerExceptions;

    @Transactional(readOnly = true)
    List<UserDtoResponse> getAll() throws BadRequestExceptions;

    @Transactional
    UserDtoResponse saveUser(UserDtoRequest userDtoRequest) throws IllegalArgumentExceptions, BadRequestExceptions;

    @Transactional
    UserDtoResponse updateUser(Long id, UserDtoRequest userDtoRequest) throws NullPointerExceptions, IllegalArgumentExceptions, BadRequestExceptions;

    @Transactional
    void deleteUser(Long id) throws IllegalArgumentExceptions, BadRequestExceptions;

}
