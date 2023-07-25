package com.iudigital.appspringsecjwt.service.implement;

import com.iudigital.appspringsecjwt.dto.request.RoleDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;
import com.iudigital.appspringsecjwt.dto.response.RoleDtoResponse;
import com.iudigital.appspringsecjwt.exception.BadRequestExceptions;
import com.iudigital.appspringsecjwt.exception.NotFoundExceptions;
import com.iudigital.appspringsecjwt.exception.NullPointerExceptions;
import com.iudigital.appspringsecjwt.exception.RestExceptions;
import com.iudigital.appspringsecjwt.model.Role;
import com.iudigital.appspringsecjwt.repository.RoleRepository;
import com.iudigital.appspringsecjwt.service.ConstantService;
import com.iudigital.appspringsecjwt.service.interfaces.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public RoleDtoResponse updateRole(Long id, RoleDtoRequest roleDtoRequest) throws RestExceptions {

        boolean existsRoleById = roleRepository.existsById(id);

        if(!existsRoleById){
            throw new BadRequestExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.BAD_REQUEST)
                            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                            .status(HttpStatus.BAD_REQUEST.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }

        Role role = roleRepository.findById(id).orElse(null);

        if(role==null){
            throw new NotFoundExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.INFO_NOT_FOUND)
                            .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                            .status(HttpStatus.NOT_FOUND.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }

        if (roleDtoRequest.getRol().isEmpty()) {
            throw new NullPointerExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.NOT_FOUND)
                            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                            .status(HttpStatus.BAD_REQUEST.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }

        try {


            role.setRol("ROLE_"+roleDtoRequest.getRol().toUpperCase());

            roleRepository.save(role);

            return RoleDtoResponse.builder()
                    .id(role.getId())
                    .rol(role.getRol())
                    .build();
        } catch (NullPointerException e) {
            throw new NullPointerExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.NOT_FOUND)
                            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                            .status(HttpStatus.BAD_REQUEST.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }catch (Exception e){
            throw new RestExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.ERROR)
                            .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }
    }

    @Override
    public void deleteRole(Long id) {

          boolean existsRole = roleRepository.existsById(id);

            if(!existsRole){
                throw new NullPointerException();
            }

            roleRepository.deleteById(id);
    }

    public boolean existsRole(Long id) {

            return roleRepository.existsById(id);
    }
}



