package com.iudigital.appspringsecjwt.service.interfaces;

import com.iudigital.appspringsecjwt.dto.request.CaseDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CaseDtoResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICaseService {

    @Transactional(readOnly = true)
    List<CaseDtoResponse> getAll();

    @Transactional
    CaseDtoResponse saveCase(CaseDtoRequest caseDtoRequest);

    @Transactional(readOnly = true)
    CaseDtoResponse getCaseById(Long id);

    void deleteCase(Long id);

    CaseDtoResponse updateCase(Long id, CaseDtoRequest caseDtoRequest);
}
