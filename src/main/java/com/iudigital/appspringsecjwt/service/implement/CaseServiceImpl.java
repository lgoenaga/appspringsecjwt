package com.iudigital.appspringsecjwt.service.implement;

import com.iudigital.appspringsecjwt.dto.request.CaseDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CaseDtoResponse;
import com.iudigital.appspringsecjwt.model.Case;
import com.iudigital.appspringsecjwt.repository.CaseRepository;
import com.iudigital.appspringsecjwt.repository.CrimeRepository;
import com.iudigital.appspringsecjwt.repository.UserRepository;
import com.iudigital.appspringsecjwt.service.interfaces.ICaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CaseServiceImpl implements ICaseService {

    final
    CaseRepository caseRepository;
    final
    UserRepository userRepository;
    final
    CrimeRepository crimeRepository;


    @Override
    public List<CaseDtoResponse> getAll() {

        List<Case> cases = caseRepository.findAll();

        return cases.stream().map(caseCrime ->
                CaseDtoResponse.builder()
                        .id(caseCrime.getId())
                        .description(caseCrime.getDescription())
                        .dateCase(caseCrime.getDateCase())
                        .altitude(caseCrime.getAltitude())
                        .latitude(caseCrime.getLatitude())
                        .longitude(caseCrime.getLongitude())
                        .createdAt(caseCrime.getCreatedAt())
                        .updatedAt(caseCrime.getUpdatedAt())
                        .user_id(caseCrime.getUser().getId())
                        .crime_id(caseCrime.getCrime().getId())
                        .build()
        ).toList();

    }

    @Override
    public CaseDtoResponse getCaseById(Long id) {

        Case caseCrime = caseRepository.findById(id).orElseThrow(NullPointerException::new);

        return CaseDtoResponse.builder()
                .id(caseCrime.getId())
                .description(caseCrime.getDescription())
                .dateCase(caseCrime.getDateCase())
                .altitude(caseCrime.getAltitude())
                .latitude(caseCrime.getLatitude())
                .longitude(caseCrime.getLongitude())
                .createdAt(caseCrime.getCreatedAt())
                .updatedAt(caseCrime.getUpdatedAt())
                .user_id(caseCrime.getUser().getId())
                .crime_id(caseCrime.getCrime().getId())
                .build();
    }
    @Override
    public CaseDtoResponse saveCase(CaseDtoRequest caseDtoRequest) {

        boolean userExist = userRepository.existsById(caseDtoRequest.getUser_id());
        boolean crimeExist = crimeRepository.existsById(caseDtoRequest.getCrime_id());

        if (!userExist || !crimeExist) {
            throw new IllegalArgumentException();
        }

        Case caseCrime = new Case();

        caseCrime.setDescription(caseDtoRequest.getDescription());
        caseCrime.setDateCase(caseDtoRequest.getDateCase());
        caseCrime.setAltitude(caseDtoRequest.getAltitude());
        caseCrime.setLatitude(caseDtoRequest.getLatitude());
        caseCrime.setLongitude(caseDtoRequest.getLongitude());
        caseCrime.setCreatedAt(LocalDate.now());
        caseCrime.setUpdatedAt(LocalDate.now());
        caseCrime.setUser(userRepository.getReferenceById(caseDtoRequest.getUser_id()));
        caseCrime.setCrime(crimeRepository.getReferenceById(caseDtoRequest.getCrime_id()));
        caseCrime.setIsVisibility(true);
        caseCrime.setRmiUrl("");
        caseCrime.setUrlMap("");

        caseRepository.save(caseCrime);

        return CaseDtoResponse.builder()
                .id(caseCrime.getId())
                .description(caseCrime.getDescription())
                .dateCase(caseCrime.getDateCase())
                .altitude(caseCrime.getAltitude())
                .latitude(caseCrime.getLatitude())
                .longitude(caseCrime.getLongitude())
                .createdAt(caseCrime.getCreatedAt())
                .updatedAt(caseCrime.getUpdatedAt())
                .user_id(caseCrime.getUser().getId())
                .crime_id(caseCrime.getCrime().getId())
                .build();
    }

    @Override
    public CaseDtoResponse updateCase(Long id, CaseDtoRequest caseDtoRequest) {

        boolean caseExist = caseRepository.existsById(id);
        boolean userExist = userRepository.existsById(caseDtoRequest.getUser_id());
        boolean crimeExist = crimeRepository.existsById(caseDtoRequest.getCrime_id());

        if (!caseExist || !userExist || !crimeExist) {
            throw new IllegalArgumentException();
        }

        Case caseCrime = caseRepository.findById(id).orElseThrow(NullPointerException::new);

        caseCrime.setDescription(caseDtoRequest.getDescription());
        caseCrime.setDateCase(caseDtoRequest.getDateCase());
        caseCrime.setAltitude(caseDtoRequest.getAltitude());
        caseCrime.setLatitude(caseDtoRequest.getLatitude());
        caseCrime.setLongitude(caseDtoRequest.getLongitude());
        caseCrime.setUpdatedAt(LocalDate.now());
        caseCrime.setUser(userRepository.getReferenceById(caseDtoRequest.getUser_id()));
        caseCrime.setCrime(crimeRepository.getReferenceById(caseDtoRequest.getCrime_id()));

        caseRepository.save(caseCrime);

        return CaseDtoResponse.builder()
                .id(caseCrime.getId())
                .description(caseCrime.getDescription())
                .dateCase(caseCrime.getDateCase())
                .altitude(caseCrime.getAltitude())
                .latitude(caseCrime.getLatitude())
                .longitude(caseCrime.getLongitude())
                .createdAt(caseCrime.getCreatedAt())
                .updatedAt(caseCrime.getUpdatedAt())
                .user_id(caseCrime.getUser().getId())
                .crime_id(caseCrime.getCrime().getId())
                .build();
    }

    @Override
    public void deleteCase(Long id) {

        boolean caseExist = caseRepository.existsById(id);

        if (!caseExist) {
            throw new NullPointerException();
        }

        caseRepository.deleteById(id);
    }

}
