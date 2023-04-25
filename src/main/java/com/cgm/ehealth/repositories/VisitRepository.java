package com.cgm.ehealth.repositories;

import com.cgm.ehealth.models.Visit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VisitRepository extends ReactiveMongoRepository<Visit, String> {
}
