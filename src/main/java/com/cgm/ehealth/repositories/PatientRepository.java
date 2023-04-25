package com.cgm.ehealth.repositories;

import com.cgm.ehealth.models.Patient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PatientRepository extends ReactiveMongoRepository<Patient, String> {
}
