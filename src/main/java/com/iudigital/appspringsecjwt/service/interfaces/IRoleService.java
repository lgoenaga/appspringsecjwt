package com.iudigital.appspringsecjwt.service.interfaces;

import com.iudigital.appspringsecjwt.dto.request.RoleDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.RoleDtoResponse;
import com.iudigital.appspringsecjwt.exception.BadRequestExceptions;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;
import com.iudigital.appspringsecjwt.exception.NullPointerExceptions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IRoleService {


    List<RoleDtoResponse> getAll() throws NullPointerExceptions, BadRequestExceptions;


    RoleDtoResponse getRolById(Long id) throws NullPointerExceptions, IllegalArgumentExceptions;


    RoleDtoResponse saveRole(RoleDtoRequest roleDtoRequest) throws BadRequestExceptions, IllegalArgumentExceptions;


    RoleDtoResponse updateRole(Long id, RoleDtoRequest roleDtoRequest) throws NullPointerExceptions, IllegalArgumentExceptions, BadRequestExceptions;



    void deleteRole(Long id) throws IllegalArgumentExceptions;
}
