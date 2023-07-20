package com.iudigital.appspringsecjwt.repository;

import com.iudigital.appspringsecjwt.model.Crime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrimeRepository extends JpaRepository<Crime, Long>{

}
