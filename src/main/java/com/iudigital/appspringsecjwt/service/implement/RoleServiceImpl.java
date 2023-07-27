package com.iudigital.appspringsecjwt.service.implement;

import com.iudigital.appspringsecjwt.dto.request.RoleDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;
import com.iudigital.appspringsecjwt.dto.response.RoleDtoResponse;
import com.iudigital.appspringsecjwt.exception.BadRequestExceptions;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;
import com.iudigital.appspringsecjwt.exception.NullPointerExceptions;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private static final String UPDATE_ROLE = "Role Update";
    private static final String DELETE_ROLE = "Role Delete";
    private static final String SAVE_ROLE = "Role Save";
    private static final String GET_ROL = "Role Get";

    private static final String ROLE = "ROLE_";

    private static final Logger logger  = Logger.getLogger(RoleServiceImpl.class.getName());
    private static final String VERIFY_ROLE = "Verify Role";

    final RoleRepository roleRepository;

    public void verifyRoleId(Long id) throws IllegalArgumentExceptions {

        boolean existsRole = roleRepository.existsById(id);

        if(!existsRole){
            logger.warning(ConstantService.ILLEGAL_ARGUMENT + " = " + ConstantService.NOT_ID + " method " + VERIFY_ROLE);
            throw new IllegalArgumentExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.NOT_ID + " method " + VERIFY_ROLE)
                            .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                            .status(HttpStatus.NOT_FOUND.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }
    }
    @Override
    @Transactional(readOnly = true)
    public List<RoleDtoResponse> getAll() throws NullPointerExceptions {

        List<Role> roles = roleRepository.findAll();

        logger.info(ConstantService.MODEL_ROLE + " " + ConstantService.SUCCESSFULLY);
        return roles.stream().map(role ->
                RoleDtoResponse.builder()
                        .id(role.getId())
                        .rol(role.getRol())
                        .build()
        ).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDtoResponse getRolById(Long id) throws NullPointerExceptions, IllegalArgumentExceptions {

        verifyRoleId(id);

        boolean existsRole = roleRepository.existsById(id);

        Role role = roleRepository.findById(id).orElseThrow(null);

        if(!existsRole || role == null){
            logger.warning(ConstantService.BAD_REQUEST + " = " + GET_ROL);
            throw new NullPointerExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.BAD_REQUEST + " = " + GET_ROL)
                            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                            .status(HttpStatus.BAD_REQUEST.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }

        return RoleDtoResponse.builder()
                .id(role.getId())
                .rol(role.getRol())
                .build();
    }

    @Override
    @Transactional
    public RoleDtoResponse saveRole(RoleDtoRequest roleDtoRequest) throws BadRequestExceptions {

        String roleDtoRequestSave = ROLE+roleDtoRequest.getRol().toUpperCase();

        boolean existsRole = roleRepository.existsByRol(roleDtoRequestSave);

        if(existsRole){
            logger.warning(ConstantService.INFO_FOUND + " =  " + SAVE_ROLE);
            throw new BadRequestExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.INFO_FOUND + " =  " + SAVE_ROLE)
                            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                            .status(HttpStatus.BAD_REQUEST.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }

        Role role = Role.builder()
                    .rol(roleDtoRequestSave)
                    .build();

        roleRepository.save(role);

            return RoleDtoResponse.builder()
                    .id(role.getId())
                    .rol(role.getRol())
                    .build();


    }

    @Override
    @Transactional
    public RoleDtoResponse updateRole(Long id, RoleDtoRequest roleDtoRequest) throws BadRequestExceptions, IllegalArgumentExceptions, NullPointerExceptions {

        String roleDtoRequestUpdate = ROLE+roleDtoRequest.getRol().toUpperCase();

        verifyRoleId(id);

        RoleDtoResponse role = getRolById(id);

        boolean existsRole = roleRepository.existsByRol(roleDtoRequestUpdate);

        if(existsRole  && !role.getRol().equals(roleDtoRequestUpdate)){
            logger.warning(ConstantService.INFO_FOUND + " =  " + UPDATE_ROLE);
            throw new BadRequestExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.INFO_FOUND + " =  " + UPDATE_ROLE)
                            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                            .status(HttpStatus.BAD_REQUEST.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }

        role.setRol(roleDtoRequestUpdate);

        Role roleUpdate = Role.builder()
                .id(role.getId())
                .rol(role.getRol())
                .build();

        roleRepository.save(roleUpdate);

        return RoleDtoResponse.builder()
                    .id(roleUpdate.getId())
                    .rol(roleUpdate.getRol())
                    .build();
    }

    @Override
    @Transactional
    public void deleteRole(Long id) throws IllegalArgumentExceptions {

        verifyRoleId(id);

        try {

            roleRepository.deleteById(id);
           if (!roleRepository.existsById(id)) {
               logger.info(DELETE_ROLE + " " + ConstantService.SUCCESSFULLY);
           }

        }  catch (Exception e) {
            logger.log(Level.SEVERE, format("{0} = {1} method {2}", ConstantService.VIOLATION_CONSTRAINT, e.getMessage(), DELETE_ROLE));
        }
    }
}



