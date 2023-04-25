package com.cgm.ehealth.controllers;

import com.cgm.ehealth.dtos.*;
import com.cgm.ehealth.services.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/v1/visits")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<VisitResponseDTO> addVisit(@Valid @RequestBody NewVisitDTO newVisitDTO) throws Exception {
        return visitService.addVisit(newVisitDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<VisitDetailsDTO> findVisitById(@PathVariable String id) throws ExecutionException, InterruptedException {
        return visitService.findVisitById(id);
    }

    @GetMapping("/types")
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<VisitType>> findVisitTypes() {
        List<VisitType> visitTypeList = List.of(VisitType.values());
        return Mono.just(visitTypeList);
    }

    @GetMapping("/reasons")
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<VisitReason>> findVisitReasons() {
        List<VisitReason> visitTypeList = List.of(VisitReason.values());
        return Mono.just(visitTypeList);
    }
}
