package com.iudigital.appspringsecjwt.service.interfaces;

import com.iudigital.appspringsecjwt.dto.request.CrimeDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CrimeDtoResponse;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;

import java.util.List;

public interface ICrimeService {


        List<CrimeDtoResponse> getAll();


        CrimeDtoResponse saveCrime(CrimeDtoRequest crimeDtoRequest);


        CrimeDtoResponse getCrimeById(Long id);


        void deleteCrime(Long id) throws IllegalArgumentExceptions;


        CrimeDtoResponse updateCrime(Long id, CrimeDtoRequest crimeDtoRequest);
}
