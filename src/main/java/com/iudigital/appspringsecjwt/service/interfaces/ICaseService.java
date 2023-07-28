package com.iudigital.appspringsecjwt.service.interfaces;

import com.iudigital.appspringsecjwt.dto.request.CaseDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CaseDtoResponse;
import com.iudigital.appspringsecjwt.exception.BadRequestExceptions;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;
import com.iudigital.appspringsecjwt.exception.NullPointerExceptions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICaseService {

    @Transactional(readOnly = true)
    List<CaseDtoResponse> getAll() throws BadRequestExceptions;

    @Transactional
    CaseDtoResponse saveCase(CaseDtoRequest caseDtoRequest) throws IllegalArgumentExceptions, BadRequestExceptions;

    @Transactional(readOnly = true)
    CaseDtoResponse getCaseById(Long id) throws NullPointerExceptions;

    void deleteCase(Long id) throws IllegalArgumentExceptions, BadRequestExceptions;

    CaseDtoResponse updateCase(Long id, CaseDtoRequest caseDtoRequest) throws IllegalArgumentExceptions, NullPointerExceptions, BadRequestExceptions;
}
