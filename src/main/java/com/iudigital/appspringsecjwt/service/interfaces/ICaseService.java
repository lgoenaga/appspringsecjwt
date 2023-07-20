package com.iudigital.appspringsecjwt.service.interfaces;

import com.iudigital.appspringsecjwt.dto.request.CaseDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CaseDtoResponse;

import java.util.List;

public interface ICaseService {


    List<CaseDtoResponse> getAll();

    CaseDtoResponse addCase(CaseDtoRequest caseDtoRequest);
}
