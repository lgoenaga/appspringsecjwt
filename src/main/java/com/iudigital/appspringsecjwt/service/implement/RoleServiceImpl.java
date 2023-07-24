package com.iudigital.appspringsecjwt.service.implement;

import com.iudigital.appspringsecjwt.dto.request.RoleDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.RoleDtoResponse;
import com.iudigital.appspringsecjwt.model.Role;
import com.iudigital.appspringsecjwt.repository.RoleRepository;
import com.iudigital.appspringsecjwt.service.interfaces.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    final RoleRepository roleRepository;

    @Override
    public List<RoleDtoResponse> getAll() {

        List<Role> roles = roleRepository.findAll();

          return roles.stream().map(role ->
                RoleDtoResponse.builder()
                        .id(role.getId())
                        .rol(role.getRol())
                        .build()
            ).toList();
    }

    @Override
    public RoleDtoResponse getRolById(Long id) {

        Role role = roleRepository.findById(id).orElse(null);

        if(role == null){
            throw new NullPointerException();
        }

        return RoleDtoResponse.builder()
                .id(role.getId())
                .rol(role.getRol())
                .build();
    }

    @Override
    public RoleDtoResponse saveRole(RoleDtoRequest roleDtoRequest) {

        boolean existsRole = roleRepository.existsByRol(roleDtoRequest.getRol());

        if(existsRole){
            throw new IllegalArgumentException();
        }

        Role role = Role.builder()
                .rol("ROLE_"+roleDtoRequest.getRol().toUpperCase())
                .build();

        roleRepository.save(role);

        return RoleDtoResponse.builder()
                .id(role.getId())
                .rol(role.getRol())
                .build();
    }

    @Override
    public RoleDtoResponse updateRole(Long id, RoleDtoRequest roleDtoRequest) {

        Role role = roleRepository.findById(id).orElse(null);

        if(role == null){
            throw new NullPointerException();
        }

        role.setRol("ROLE_"+roleDtoRequest.getRol().toUpperCase());

        roleRepository.save(role);

        return RoleDtoResponse.builder()
                .id(role.getId())
                .rol(role.getRol())
                .build();
    }

    @Override
    public void deleteRole(Long id) {

          boolean existsRole = roleRepository.existsById(id);

            if(!existsRole){
                throw new NullPointerException();
            }

            roleRepository.deleteById(id);
    }
}



