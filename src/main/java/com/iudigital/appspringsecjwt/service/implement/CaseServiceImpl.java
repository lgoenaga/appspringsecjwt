package com.iudigital.appspringsecjwt.service.implement;

import com.iudigital.appspringsecjwt.dto.request.CaseDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CaseDtoResponse;
import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;
import com.iudigital.appspringsecjwt.exception.BadRequestExceptions;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;
import com.iudigital.appspringsecjwt.exception.NullPointerExceptions;
import com.iudigital.appspringsecjwt.model.Case;
import com.iudigital.appspringsecjwt.repository.CaseRepository;
import com.iudigital.appspringsecjwt.repository.CrimeRepository;
import com.iudigital.appspringsecjwt.repository.UserRepository;
import com.iudigital.appspringsecjwt.service.ConstantService;
import com.iudigital.appspringsecjwt.service.interfaces.ICaseService;
import com.iudigital.appspringsecjwt.util.VerifyExist;
import com.iudigital.appspringsecjwt.util.VerifyNotExist;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CaseServiceImpl implements ICaseService {

    private static final String VERIFY_CASE = "Verify Case";

    final
    CaseRepository caseRepository;
    final
    UserRepository userRepository;
    final
    CrimeRepository crimeRepository;

    VerifyExist verifyExist;
    VerifyNotExist verifyNotExist;


    @Override
    public List<CaseDtoResponse> getAll() throws BadRequestExceptions {

        try {
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
        } catch (Exception e) {
            throw new BadRequestExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.BAD_REQUEST)
                            .error(ConstantService.BAD_REQUEST)
                            .status(HttpStatus.BAD_REQUEST.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }

    }

    @Override
    public CaseDtoResponse getCaseById(Long id) throws NullPointerExceptions {

        Case caseCrime = caseRepository.findById(id).orElseThrow(
                () -> new NullPointerExceptions(
                        ErrorDtoResponse.builder()
                                .message(ConstantService.NOT_FOUND)
                                .error(ConstantService.NOT_FOUND)
                                .status(HttpStatus.NOT_FOUND.value())
                                .date(LocalDateTime.now())
                                .build()
                )
        );

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
    public CaseDtoResponse saveCase(CaseDtoRequest caseDtoRequest) throws IllegalArgumentExceptions, BadRequestExceptions {

        boolean userExist = userRepository.existsById(caseDtoRequest.getUser_id());

        boolean crimeExist = crimeRepository.existsById(caseDtoRequest.getCrime_id());

        if (!userExist || !crimeExist) {
            throw new IllegalArgumentExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.INFO_NOT_FOUND)
                            .error(ConstantService.NOT_FOUND)
                            .status(HttpStatus.NOT_FOUND.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }

        try {
            CaseDtoResponse caseUpdate = CaseDtoResponse.builder()
                    .description(caseDtoRequest.getDescription())
                    .dateCase(caseDtoRequest.getDateCase())
                    .altitude(caseDtoRequest.getAltitude())
                    .latitude(caseDtoRequest.getLatitude())
                    .longitude(caseDtoRequest.getLongitude())
                    .createdAt(caseDtoRequest.getCreatedAt())
                    .updatedAt(caseDtoRequest.getUpdatedAt())
                    .user_id(caseDtoRequest.getUser_id())
                    .crime_id(caseDtoRequest.getCrime_id())
                    .build();

            Case caseCrime = new Case();

            caseCrime.setDescription(caseUpdate.getDescription());
            caseCrime.setDateCase(caseUpdate.getDateCase());
            caseCrime.setAltitude(caseUpdate.getAltitude());
            caseCrime.setLatitude(caseUpdate.getLatitude());
            caseCrime.setLongitude(caseUpdate.getLongitude());
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

        } catch (Exception e) {
            throw new BadRequestExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.BAD_REQUEST)
                            .error(ConstantService.BAD_REQUEST)
                            .status(HttpStatus.BAD_REQUEST.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }


    }

    @Override
    public CaseDtoResponse updateCase(Long id, CaseDtoRequest caseDtoRequest) throws IllegalArgumentExceptions, NullPointerExceptions, BadRequestExceptions {

        boolean caseExist = caseRepository.existsById(id);
        verifyExist.verify(caseExist, ConstantService.INFO_FOUND + ConstantService.METHOD + VERIFY_CASE);

        boolean userExist = userRepository.existsById(caseDtoRequest.getUser_id());
        verifyExist.verify(userExist, ConstantService.INFO_FOUND + ConstantService.METHOD + VERIFY_CASE);

        boolean crimeExist = crimeRepository.existsById(caseDtoRequest.getCrime_id());
        verifyExist.verify(crimeExist, ConstantService.INFO_FOUND + ConstantService.METHOD + VERIFY_CASE);


        Case caseCrime = caseRepository.findById(id).orElseThrow(
                () -> new NullPointerExceptions(
                        ErrorDtoResponse.builder()
                                .message(ConstantService.NOT_FOUND)
                                .error(ConstantService.NOT_FOUND)
                                .status(HttpStatus.NOT_FOUND.value())
                                .date(LocalDateTime.now())
                                .build())
        );

        try {
            CaseDtoRequest caseUpdate = CaseDtoRequest.builder()
                    .description(caseDtoRequest.getDescription())
                    .dateCase(caseDtoRequest.getDateCase())
                    .altitude(caseDtoRequest.getAltitude())
                    .latitude(caseDtoRequest.getLatitude())
                    .longitude(caseDtoRequest.getLongitude())
                    .user_id(caseDtoRequest.getUser_id())
                    .crime_id(caseDtoRequest.getCrime_id())
                    .build();

            caseCrime.setDescription(caseUpdate.getDescription());
            caseCrime.setDateCase(caseUpdate.getDateCase());
            caseCrime.setAltitude(caseUpdate.getAltitude());
            caseCrime.setLatitude(caseUpdate.getLatitude());
            caseCrime.setLongitude(caseUpdate.getLongitude());
            caseCrime.setUpdatedAt(LocalDate.now());
            caseCrime.setUser(userRepository.getReferenceById(caseUpdate.getUser_id()));
            caseCrime.setCrime(crimeRepository.getReferenceById(caseUpdate.getCrime_id()));

            caseRepository.save(caseCrime);

        } catch (Exception e) {
            throw new BadRequestExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.BAD_REQUEST)
                            .error(ConstantService.BAD_REQUEST)
                            .status(HttpStatus.BAD_REQUEST.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }

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
    public void deleteCase(Long id) throws IllegalArgumentExceptions, BadRequestExceptions {

        verifyExist = new VerifyExist();

        boolean caseExist = caseRepository.existsById(id);

        verifyNotExist.verify(caseExist, ConstantService.NOT_ID + " method " + VERIFY_CASE);

        try {
            caseRepository.deleteById(id);
        } catch (Exception e) {
            throw new BadRequestExceptions(
                    ErrorDtoResponse.builder()
                            .message(ConstantService.BAD_REQUEST)
                            .error(ConstantService.BAD_REQUEST)
                            .status(HttpStatus.BAD_REQUEST.value())
                            .date(LocalDateTime.now())
                            .build()
            );
        }
    }

}
