package com.iudigital.appspringsecjwt.service.implement;

import com.iudigital.appspringsecjwt.dto.request.CrimeDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CrimeDtoResponse;
import com.iudigital.appspringsecjwt.exception.IllegalArgumentExceptions;
import com.iudigital.appspringsecjwt.model.Crime;
import com.iudigital.appspringsecjwt.model.User;
import com.iudigital.appspringsecjwt.repository.CrimeRepository;
import com.iudigital.appspringsecjwt.repository.UserRepository;
import com.iudigital.appspringsecjwt.service.ConstantService;
import com.iudigital.appspringsecjwt.service.interfaces.ICrimeService;
import com.iudigital.appspringsecjwt.util.VerifyNotExist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class CrimeServiceImpl implements ICrimeService {

    VerifyNotExist verifyNotExist = new VerifyNotExist();
    private static final Logger logger  = Logger.getLogger(CrimeServiceImpl.class.getName());
    private static final String VERIFY_CRIME = "Verify Crime";

    private static final String DELETE_CRIME = "Crime Delete";

    private static final String UPDATE_CRIME = "Crime Update";

    private static final String SAVE_CRIME = "Crime Save";

    private static final String GET_CRIME = "Crime Get";

    final 
    CrimeRepository crimeRepository;
    final
    UserRepository userRepository;

    @Override
    public List<CrimeDtoResponse> getAll(){
        
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
    }

    @Override
    public CrimeDtoResponse getCrimeById(Long id) {

        Crime crime = crimeRepository.findById(id).orElseThrow(NullPointerException::new);

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
    public CrimeDtoResponse saveCrime(CrimeDtoRequest crimeDtoRequest) {

        boolean isCrime = crimeRepository.existsByName(crimeDtoRequest.getName());

        if(isCrime){
            throw new IllegalArgumentException();
        }

        User user = userRepository.findById(crimeDtoRequest.getUserId()).orElseThrow(NullPointerException::new);

        Crime crime = new Crime();

        crime.setName(crimeDtoRequest.getName().toUpperCase());
        crime.setDescription(crimeDtoRequest.getDescription());
        crime.setCreatedAt(LocalDate.now());
        crime.setUpdatedAt(LocalDate.now());
        crime.setUser(user);

        crimeRepository.save(crime);

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
    public CrimeDtoResponse updateCrime(Long id, CrimeDtoRequest crimeDtoRequest) {

        Crime crime = crimeRepository.findById(id).orElseThrow(NullPointerException::new);
        User user = userRepository.findById(crimeDtoRequest.getUserId()).orElseThrow(IllegalArgumentException::new);

        if (!Objects.equals(crimeDtoRequest.getName(), crime.getName())) {
            boolean isCrime = crimeRepository.existsByName(crimeDtoRequest.getName());

            if(isCrime){
                throw new IllegalArgumentException();
            }
        }

        crime.setName(crimeDtoRequest.getName().toUpperCase());
        crime.setDescription(crimeDtoRequest.getDescription());
        crime.setUser(user);
        crime.setUpdatedAt(LocalDate.now());

        crimeRepository.save(crime);

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
    public void deleteCrime(Long id) throws IllegalArgumentExceptions {

        boolean isCrime = crimeRepository.existsById(id);

        verifyNotExist.verify(isCrime, ConstantService.NOT_ID + " method " + VERIFY_CRIME);

        try {
            crimeRepository.deleteById(id);
            if (!crimeRepository.existsById(id)) {
                logger.info(ConstantService.MODEL_CRIME + " " + ConstantService.SUCCESSFULLY);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, format("{0} = {1} method {2}", ConstantService.VIOLATION_CONSTRAINT, e.getMessage(), DELETE_CRIME));
        }
    }

}
