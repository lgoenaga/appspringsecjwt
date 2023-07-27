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

import com.iudigital.appspringsecjwt.util.VerifyNotExist;
import com.iudigital.appspringsecjwt.util.VerifyExist;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    VerifyNotExist verifyNotExist = new VerifyNotExist();
    VerifyExist verifyExist = new VerifyExist();

    private static final String UPDATE_ROLE = "Role Update";
    private static final String DELETE_ROLE = "Role Delete";
    private static final String SAVE_ROLE = "Role Save";
    private static final String GET_ROL = "Role Get";

    private static final String ROLE = "ROLE_";

    private static final Logger logger  = Logger.getLogger(RoleServiceImpl.class.getName());
    private static final String VERIFY_ROLE = "Verify Role";

    final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RoleDtoResponse> getAll() throws BadRequestExceptions {

        List<Role> roles = roleRepository.findAll();

        if (roles.isEmpty()) {
            logger.warning(ConstantService.BAD_REQUEST + " = " + ConstantService.METHOD + GET_ROL);
            throw new BadRequestExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.BAD_REQUEST + " = " + ConstantService.METHOD + GET_ROL)
                            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                            .status(HttpStatus.BAD_REQUEST.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }

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
    public RoleDtoResponse getRolById(Long id) throws IllegalArgumentExceptions, NullPointerExceptions {

        boolean existsRole = roleRepository.existsById(id);
        verifyNotExist.verify(existsRole, ConstantService.NOT_ID + ConstantService.METHOD + GET_ROL);

        Role role = roleRepository.findById(id).orElseThrow(null);

        if(!existsRole || role == null){
            logger.warning(ConstantService.NOT_FOUND + " = " + GET_ROL);
            throw new NullPointerExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.NOT_FOUND + " = " + GET_ROL)
                            .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                            .status(HttpStatus.NOT_FOUND.value())
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
    public RoleDtoResponse saveRole(RoleDtoRequest roleDtoRequest) throws IllegalArgumentExceptions {

        String roleDtoRequestSave = ROLE+roleDtoRequest.getRol().toUpperCase();

        boolean existsRole = roleRepository.existsByRol(roleDtoRequestSave);
        verifyExist.verify(existsRole, ConstantService.INFO_FOUND + ConstantService.METHOD + SAVE_ROLE);

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
    public RoleDtoResponse updateRole(Long id, RoleDtoRequest roleDtoRequest) throws IllegalArgumentExceptions, NullPointerExceptions {

        String roleDtoRequestUpdate = ROLE+roleDtoRequest.getRol().toUpperCase();

        boolean existsRole = roleRepository.existsById(id);
        verifyNotExist.verify(existsRole, ConstantService.NOT_ID + ConstantService.METHOD + VERIFY_ROLE);

        RoleDtoResponse role = getRolById(id);
        if(!role.getRol().equals(roleDtoRequestUpdate)){

            boolean existsRoleUpdate = roleRepository.existsByRol(roleDtoRequestUpdate);
            verifyExist.verify(existsRoleUpdate, ConstantService.INFO_FOUND + ConstantService.METHOD + UPDATE_ROLE);
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

        boolean existsRole = roleRepository.existsById(id);

        verifyNotExist.verify(existsRole, ConstantService.NOT_ID + ConstantService.METHOD + VERIFY_ROLE);

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



