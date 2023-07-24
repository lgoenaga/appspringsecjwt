package com.iudigital.appspringsecjwt.service.interfaces;

import com.iudigital.appspringsecjwt.dto.request.CrimeDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CrimeDtoResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICrimeService {

        @Transactional(readOnly = true)
        List<CrimeDtoResponse> getAll();

        @Transactional
        CrimeDtoResponse addCrime(CrimeDtoRequest crimeDtoRequest);
}
