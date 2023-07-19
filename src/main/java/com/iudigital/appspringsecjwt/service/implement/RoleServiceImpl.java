package com.iudigital.appspringsecjwt.service.implement;

import com.iudigital.appspringsecjwt.model.Role;
import com.iudigital.appspringsecjwt.repository.RoleRepository;
import com.iudigital.appspringsecjwt.service.interfaces.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    final
    RoleRepository roleRepository;
    public List<Role> findByRol(String roleUser) {
        return roleRepository.findByRol(roleUser);
    }
}
