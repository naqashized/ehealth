package com.cgm.ehealth.services;

import com.cgm.ehealth.dtos.NewVisitDTO;
import com.cgm.ehealth.dtos.PatientDetailsDTO;
import com.cgm.ehealth.dtos.VisitDetailsDTO;
import com.cgm.ehealth.dtos.VisitResponseDTO;
import com.cgm.ehealth.models.Patient;
import com.cgm.ehealth.models.Visit;
import com.cgm.ehealth.repositories.PatientRepository;
import com.cgm.ehealth.repositories.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService{
    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    @Override
    public Mono<VisitResponseDTO> addVisit(NewVisitDTO newVisitDTO) throws ExecutionException, InterruptedException {
        Visit visit = new Visit();
        visit.setFamilyHistory(newVisitDTO.familyHistory());
        visit.setVisitTime(newVisitDTO.visitTime());
        visit.setReason(newVisitDTO.reason());
        visit.setType(newVisitDTO.type());
        Mono<Patient> patientMono = patientRepository.findById(newVisitDTO.patientId()).
                switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid patientId: " + newVisitDTO.patientId())));
        visit.setPatientId(patientMono.toFuture().get().getId());

        Mono<Visit> visitMono = visitRepository.save(visit);
        return visitMono.map(response -> new VisitResponseDTO(response.getId()));
    }

    @Override
    public Mono<VisitDetailsDTO> findVisitById(String id) throws ExecutionException, InterruptedException {
        Mono<Visit> visitMono = visitRepository.findById(id).
                switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid id: " + id)));
        String patientId = visitMono.toFuture().get().getPatientId();
        Mono<Patient> patientMono = patientRepository.findById(patientId).
                switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid patientId: " + patientId)));
        PatientDetailsDTO patientDetailsDTO = patientMono.map(patient -> new PatientDetailsDTO(patient.getId(), patient.getName(),
                patient.getSurname())).toFuture().get();

        return visitMono.map(response -> new VisitDetailsDTO(response.getId(), response.getFamilyHistory(),patientDetailsDTO));
    }
}
