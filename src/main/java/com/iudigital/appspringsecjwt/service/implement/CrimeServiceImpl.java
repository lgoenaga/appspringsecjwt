package com.iudigital.appspringsecjwt.service.implement;

import com.iudigital.appspringsecjwt.dto.request.CrimeDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CrimeDtoResponse;
import com.iudigital.appspringsecjwt.dto.response.ErrorDtoResponse;
import com.iudigital.appspringsecjwt.exception.BadRequestExceptions;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;
import com.iudigital.appspringsecjwt.exception.NullPointerExceptions;
import com.iudigital.appspringsecjwt.model.Crime;
import com.iudigital.appspringsecjwt.model.User;
import com.iudigital.appspringsecjwt.repository.CrimeRepository;
import com.iudigital.appspringsecjwt.repository.UserRepository;
import com.iudigital.appspringsecjwt.service.ConstantService;
import com.iudigital.appspringsecjwt.service.interfaces.ICrimeService;
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
public class CrimeServiceImpl implements ICrimeService {

    private static final String VERIFY_USER = "Verify User";
    VerifyNotExist verifyNotExist = new VerifyNotExist();
    VerifyExist verifyExist = new VerifyExist();
    private static final String VERIFY_CRIME = "Verify Crime";

    final 
    CrimeRepository crimeRepository;
    final
    UserRepository userRepository;

    private User verifyNotUser(CrimeDtoRequest crimeDtoRequest) throws IllegalArgumentExceptions, NullPointerExceptions {
        boolean isUser = userRepository.existsById(crimeDtoRequest.getUserId());
        verifyNotExist.verify(isUser, ConstantService.NOT_ID + ConstantService.METHOD + VERIFY_USER);

        return userRepository.findById(crimeDtoRequest.getUserId()).orElseThrow(
                () -> new NullPointerExceptions(
                        ErrorDtoResponse.builder()
                                .message(ConstantService.NOT_FOUND)
                                .error(ConstantService.NOT_FOUND)
                                .status(HttpStatus.NOT_FOUND.value())
                                .date(LocalDateTime.now())
                                .build()
                )
        );

    }
    @Override
    public List<CrimeDtoResponse> getAll() throws BadRequestExceptions {

        try {
            List<Crime> crimes = crimeRepository.findAll();
            return crimes.stream().map(crime ->
                    CrimeDtoResponse.builder()
                            .id(crime.getId())
                            .name(crime.getName())
                            .description(crime.getDescription())
                            .createdAt(crime.getCreatedAt())
                            .updatedAt(crime.getUpdatedAt())
                            .userId(crime.getUser().getId())
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
    public CrimeDtoResponse getCrimeById(Long id) throws NullPointerExceptions {

        Crime crime = crimeRepository.findById(id).orElseThrow(
                () -> new NullPointerExceptions(
                        ErrorDtoResponse.builder()
                                .message(ConstantService.NOT_FOUND)
                                .error(ConstantService.NOT_FOUND)
                                .status(HttpStatus.NOT_FOUND.value())
                                .date(LocalDateTime.now())
                                .build()
                )
        );

        return CrimeDtoResponse.builder()
                .id(crime.getId())
                .name(crime.getName())
                .description(crime.getDescription())
                .createdAt(crime.getCreatedAt())
                .updatedAt(crime.getUpdatedAt())
                .userId(crime.getUser().getId())
                .build();
    }
    @Override
    public CrimeDtoResponse saveCrime(CrimeDtoRequest crimeDtoRequest) throws IllegalArgumentExceptions, NullPointerExceptions, BadRequestExceptions {

        boolean isCrime = crimeRepository.existsByName(crimeDtoRequest.getName());
        verifyExist.verify(isCrime, ConstantService.INFO_FOUND + ConstantService.METHOD + VERIFY_CRIME);

        User user = verifyNotUser(crimeDtoRequest);

        Crime crime = Crime.builder()
                .name(crimeDtoRequest.getName().toUpperCase())
                .description(crimeDtoRequest.getDescription())
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .user(user)
                .build();

        try {
            crimeRepository.save(crime);

            return CrimeDtoResponse.builder()
                    .id(crime.getId())
                    .name(crime.getName())
                    .description(crime.getDescription())
                    .createdAt(crime.getCreatedAt())
                    .updatedAt(crime.getUpdatedAt())
                    .userId(crime.getUser().getId())
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
    public CrimeDtoResponse updateCrime(Long id, CrimeDtoRequest crimeDtoRequest) throws IllegalArgumentExceptions, NullPointerExceptions, BadRequestExceptions {

        boolean isCrime = crimeRepository.existsById(id);
        verifyNotExist.verify(isCrime, ConstantService.NOT_ID + ConstantService.METHOD + VERIFY_CRIME);

        CrimeDtoResponse crimeDtoResponse = getCrimeById(id);

        if (!crimeDtoResponse.getName().toUpperCase().equals(crimeDtoRequest.getName().toUpperCase())) {
            boolean crimeName = crimeRepository.existsByName(crimeDtoRequest.getName());
            verifyExist.verify(crimeName, ConstantService.INFO_FOUND + ConstantService.METHOD + VERIFY_CRIME);
        }

        User user = verifyNotUser(crimeDtoRequest);

        Crime crime = Crime.builder()
                .id(crimeDtoResponse.getId())
                .name(crimeDtoRequest.getName().toUpperCase())
                .description(crimeDtoRequest.getDescription())
                .createdAt(crimeDtoResponse.getCreatedAt())
                .updatedAt(LocalDate.now())
                .user(user)
                .build();

        try {
            crimeRepository.save(crime);

            return CrimeDtoResponse.builder()
                    .id(crimeDtoResponse.getId())
                    .name(crime.getName())
                    .description(crime.getDescription())
                    .createdAt(crime.getCreatedAt())
                    .updatedAt(crime.getUpdatedAt())
                    .userId(crime.getUser().getId())
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
    public void deleteCrime(Long id) throws IllegalArgumentExceptions, BadRequestExceptions {

        boolean isCrime = crimeRepository.existsById(id);

        verifyNotExist.verify(isCrime, ConstantService.NOT_ID + " method " + VERIFY_CRIME);

        try {
            crimeRepository.deleteById(id);
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
