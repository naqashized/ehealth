package com.cgm.ehealth.controllers;

import com.cgm.ehealth.repositories.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Testcontainers
public class PatientControllerTests {
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private PatientRepository patientRepository;

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
    void find_all_patients(){
        long patientCount = patientRepository.findAll().count().block();

        webTestClient.get().uri("/v1/patients")
                .exchange().expectStatus().isEqualTo(HttpStatus.ACCEPTED)
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.size()").isEqualTo(patientCount);
    }
}
