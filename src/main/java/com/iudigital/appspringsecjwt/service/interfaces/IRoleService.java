package com.iudigital.appspringsecjwt.service.interfaces;

import com.iudigital.appspringsecjwt.dto.request.RoleDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.RoleDtoResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public interface IRoleService {

    @Transactional(readOnly = true)
    List<RoleDtoResponse> getAll();

    @Transactional(readOnly = true)
    RoleDtoResponse getRolById(Long id);

    @Transactional
    RoleDtoResponse saveRole(RoleDtoRequest roleDtoRequest);

    @Transactional
    RoleDtoResponse updateRole(Long id, RoleDtoRequest roleDtoRequest);

    @Transactional
    void deleteRole(Long id);
}
