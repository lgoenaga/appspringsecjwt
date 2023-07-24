package com.iudigital.appspringsecjwt.repository;

import com.iudigital.appspringsecjwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    List<Role> findByRol(String roleUser);

    boolean existsByRol(String rol);
}
