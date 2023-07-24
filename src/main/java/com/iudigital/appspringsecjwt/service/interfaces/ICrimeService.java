package com.iudigital.appspringsecjwt.service.interfaces;

import com.iudigital.appspringsecjwt.dto.request.CrimeDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CrimeDtoResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICrimeService {

        @Transactional(readOnly = true)
        List<CrimeDtoResponse> getAll();

        @Transactional
        CrimeDtoResponse saveCrime(CrimeDtoRequest crimeDtoRequest);

        @Transactional(readOnly = true)
        CrimeDtoResponse getCrimeById(Long id);

        @Transactional
        void deleteCrime(Long id);

        @Transactional
        CrimeDtoResponse updateCrime(Long id, CrimeDtoRequest crimeDtoRequest);
}
