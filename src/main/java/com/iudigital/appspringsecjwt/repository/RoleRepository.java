package com.iudigital.appspringsecjwt.repository;

import com.iudigital.appspringsecjwt.model.Role;
import com.iudigital.appspringsecjwt.service.interfaces.IRoleService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> , IRoleService {

}
