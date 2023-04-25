package com.cgm.ehealth.controllers;

import com.cgm.ehealth.dtos.NewVisitDTO;
import com.cgm.ehealth.dtos.VisitReason;
import com.cgm.ehealth.dtos.VisitType;
import com.cgm.ehealth.models.Patient;
import com.cgm.ehealth.models.Visit;
import com.cgm.ehealth.repositories.PatientRepository;
import com.cgm.ehealth.repositories.VisitRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Testcontainers
public class VisitControllerTests {
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Container
    static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeAll
    public static void start() {
        mongoDBContainer.start();
    }

    @AfterAll
    public static void stop() {
        mongoDBContainer.stop();
    }

    @Test
    void add_new_visit(){
        Patient patient = new Patient();
        patient.setBirthDate(LocalDate.now());
        patient.setName("Tom");
        patient.setSurname("Hardy");
        patient.setSocialSecurityNumber("1234666");

       String patientId =  patientRepository.save(patient).block().getId();

        NewVisitDTO newVisitDTO = new NewVisitDTO(patientId, "No decease history",
                VisitType.HOME, VisitReason.FIRST,LocalDateTime.now());


        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        //String request = objectMapper.writeValueAsString(newVisitDTO);
        webTestClient.post().uri("/v1/visits")
                .contentType(MEDIA_TYPE_JSON_UTF8).bodyValue(newVisitDTO)
                .exchange().expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectBody()
                .jsonPath("$.id").exists();
    }

    @Test
    void find_visit_by_id(){
        String id = setupVisit();

        webTestClient.get().uri("/v1/visits/"+id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isAccepted()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.patientDetails").exists();
    }

    private String setupVisit() {
        Patient patient = new Patient();
        patient.setBirthDate(LocalDate.now());
        patient.setName("Tom");
        patient.setSurname("Hardy");
        patient.setSocialSecurityNumber(UUID.randomUUID().toString());

        String patientId =  patientRepository.save(patient).block().getId();

        Visit visit = new Visit();
        visit.setVisitTime(LocalDateTime.now());
        visit.setType(VisitType.OFFICE);
        visit.setReason(VisitReason.RECURRING);
        visit.setPatientId(patientId);

        String visitId = visitRepository.save(visit).block().getId();
        return visitId;
    }
}
