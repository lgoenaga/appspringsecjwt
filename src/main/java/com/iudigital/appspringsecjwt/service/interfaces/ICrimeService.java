package com.iudigital.appspringsecjwt.service.interfaces;

import com.iudigital.appspringsecjwt.dto.request.CrimeDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CrimeDtoResponse;
import com.iudigital.appspringsecjwt.exception.BadRequestExceptions;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;
import com.iudigital.appspringsecjwt.exception.NullPointerExceptions;

import java.util.List;

public interface ICrimeService {


        List<CrimeDtoResponse> getAll() throws NullPointerExceptions, BadRequestExceptions;


        CrimeDtoResponse saveCrime(CrimeDtoRequest crimeDtoRequest) throws IllegalArgumentExceptions, NullPointerExceptions, BadRequestExceptions;


        CrimeDtoResponse getCrimeById(Long id) throws NullPointerExceptions;


        void deleteCrime(Long id) throws IllegalArgumentExceptions, BadRequestExceptions;


        CrimeDtoResponse updateCrime(Long id, CrimeDtoRequest crimeDtoRequest) throws IllegalArgumentExceptions, NullPointerExceptions, BadRequestExceptions;
}
