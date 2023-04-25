package com.cgm.ehealth.controllers;

import com.cgm.ehealth.dtos.PatientDetailsDTO;
import com.cgm.ehealth.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientRepository patientRepository;
    @GetMapping("")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Flux<PatientDetailsDTO> findPatients() {
       return patientRepository.findAll().
               map(patient -> new PatientDetailsDTO(patient.getId(), patient.getName(), patient.getSurname()));
    }
}
