package com.cgm.ehealth.models;

import com.cgm.ehealth.dtos.VisitReason;
import com.cgm.ehealth.dtos.VisitType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@Setter
public class Visit {
    @Id
    private String id;
    private String familyHistory;
    private LocalDateTime visitTime;
    private VisitReason reason;
    private VisitType type;
    private String patientId;
}
