package com.cgm.ehealth.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Getter
@Setter
public class Patient {
    @Id
    private String id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    @Indexed(unique = true)
    private String socialSecurityNumber;

}
