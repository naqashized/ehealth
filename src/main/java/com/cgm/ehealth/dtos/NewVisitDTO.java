package com.cgm.ehealth.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record NewVisitDTO(
        @NotEmpty
        String patientId,
        String familyHistory,
        @NotNull
        VisitType type,
        @NotNull
        VisitReason reason,
        @NotNull
        LocalDateTime visitTime) {
}
