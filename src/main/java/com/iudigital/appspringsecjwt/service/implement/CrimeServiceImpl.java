package com.iudigital.appspringsecjwt.service.implement;

import com.iudigital.appspringsecjwt.dto.request.CrimeDtoRequest;
import com.iudigital.appspringsecjwt.dto.response.CrimeDtoResponse;
import com.iudigital.appspringsecjwt.model.Crime;
import com.iudigital.appspringsecjwt.model.User;
import com.iudigital.appspringsecjwt.repository.CrimeRepository;
import com.iudigital.appspringsecjwt.repository.UserRepository;
import com.iudigital.appspringsecjwt.service.ConstantService;
import com.iudigital.appspringsecjwt.service.interfaces.ICrimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrimeServiceImpl implements ICrimeService {

    final 
    CrimeRepository crimeRepository;
    final
    UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CrimeDtoResponse> getAll(){
        
        List<Crime> crimes = crimeRepository.findAll();

        return crimes.stream().map(crime ->
            CrimeDtoResponse.builder()
                    .id(crime.getId())
                    .name(crime.getName())
                    .description(crime.getDescription())
                    .userId(crime.getUser().getId())
                    .build()
        ).toList();
    }

    @Override
    @Transactional
    public CrimeDtoResponse addCrime(CrimeDtoRequest crimeDtoRequest){

        User user = userRepository.findById(crimeDtoRequest.getUserId()).orElseThrow(() -> new RuntimeException(ConstantService.NOT_FOUND));

            Crime crime = new Crime();

            crime.setName(crimeDtoRequest.getName());
            crime.setDescription(crimeDtoRequest.getDescription());
            crime.setCreatedAt(LocalDate.now());
            crime.setUpdatedAt(LocalDate.now());
            crime.setUser(user);

            crimeRepository.save(crime);

            return CrimeDtoResponse.builder()
                    .id(crime.getId())
                    .name(crime.getName())
                    .description(crime.getDescription())
                    .userId(crime.getUser().getId())
                    .build();
    }

}
