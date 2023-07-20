package com.iudigital.appspringsecjwt.service.interfaces;

import com.iudigital.appspringsecjwt.dto.request.CrimeDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CrimeDtoResponse;

import java.util.List;

public interface ICrimeService {

        List<CrimeDtoResponse> getAll();

        CrimeDtoResponse getCrimeById(Long id);

        CrimeDtoResponse addCrime(CrimeDtoRequest crimeDtoRequest);

        String deleteCrimeById(Long id);
}
