package com.cgm.ehealth.services;

import com.cgm.ehealth.dtos.NewVisitDTO;
import com.cgm.ehealth.dtos.VisitDetailsDTO;
import com.cgm.ehealth.dtos.VisitResponseDTO;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

public interface VisitService {
    Mono<VisitResponseDTO> addVisit(NewVisitDTO newVisitDTO) throws ExecutionException, InterruptedException;
    Mono<VisitDetailsDTO> findVisitById(String id) throws ExecutionException, InterruptedException;
}
