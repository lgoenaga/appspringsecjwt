package com.iudigital.appspringsecjwt.controller;

import com.iudigital.appspringsecjwt.dto.request.CrimeDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CrimeDtoResponse;
import com.iudigital.appspringsecjwt.service.interfaces.ICrimeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crimes")
public class CrimeController {

    final
    ICrimeService crimeService;
    public CrimeController(ICrimeService crimeService) {
        this.crimeService = crimeService;
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<CrimeDtoResponse>> index(){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(crimeService.getAll());
    }


    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<CrimeDtoResponse> store(@Valid @RequestBody CrimeDtoRequest crimeDtoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(crimeService.addCrime(crimeDtoRequest));
    }

}
