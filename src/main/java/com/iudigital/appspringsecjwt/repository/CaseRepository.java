package com.iudigital.appspringsecjwt.repository;

import com.iudigital.appspringsecjwt.model.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long>{


}
