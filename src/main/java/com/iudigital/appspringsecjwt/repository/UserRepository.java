package com.iudigital.appspringsecjwt.repository;

import com.iudigital.appspringsecjwt.model.User;
import com.iudigital.appspringsecjwt.service.interfaces.IUserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, IUserService {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
