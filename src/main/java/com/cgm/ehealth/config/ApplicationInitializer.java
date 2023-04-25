package com.cgm.ehealth.config;

import com.cgm.ehealth.models.Patient;
import com.cgm.ehealth.repositories.PatientRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/*
    This Spring component just ensures that some patients are already available
    in DB, so that they can used for creating visits
 */
@Component
@RequiredArgsConstructor
public class ApplicationInitializer {
    private final PatientRepository patientRepository;
    @Value("${ehealth.min.patients}")
    private int minimunPatiens;
    @PostConstruct
    public void initializePatients(){
        if(patientRepository.count().block() < minimunPatiens){
            System.out.println("Saving patient ");
            Patient patient1 = new Patient();
            patient1.setBirthDate(LocalDate.now());
            patient1.setName("Tom");
            patient1.setSurname("Hardy");
            patient1.setSocialSecurityNumber(UUID.randomUUID().toString());

            Patient patient2 = new Patient();
            patient2.setBirthDate(LocalDate.now());
            patient2.setName("David");
            patient2.setSurname("Hardy");
            patient2.setSocialSecurityNumber(UUID.randomUUID().toString());

            List<Patient> patients = List.of(patient1, patient2);

            Flux<Patient> patientFlux = patientRepository.saveAll(patients);
            patientFlux.subscribe();
        }
    }
}
