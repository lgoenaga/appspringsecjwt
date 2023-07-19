package com.iudigital.appspringsecjwt.service.interfaces;

import com.iudigital.appspringsecjwt.model.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IRoleService {
    List<Role> findByRol(String roleUser);
}
