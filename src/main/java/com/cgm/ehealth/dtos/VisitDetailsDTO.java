package com.cgm.ehealth.dtos;

public record VisitDetailsDTO(String id, String familyHistory, VisitType type, VisitReason reason, PatientDetailsDTO patientDetails) {
}
