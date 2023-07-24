package com.iudigital.appspringsecjwt.controller;

import com.iudigital.appspringsecjwt.dto.request.CaseDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CaseDtoResponse;
import com.iudigital.appspringsecjwt.service.implement.CaseServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cases")
@RequiredArgsConstructor
public class CaseController {

    final
    CaseServiceImpl caseService;


    @GetMapping
    public ResponseEntity<List<CaseDtoResponse>> index(){
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(caseService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<CaseDtoResponse> store(@Valid @RequestBody CaseDtoRequest caseDtoRequest){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(caseService.addCase(caseDtoRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
