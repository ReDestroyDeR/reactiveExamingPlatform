package ru.red.reactiveexaminingplatform.controller.exam;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Exam;
import ru.red.reactiveexaminingplatform.repository.exam.ExamRepository;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.red.reactiveexaminingplatform.util.ExamUtils.randomExam;
import static ru.red.reactiveexaminingplatform.util.UUIDUtils.UUID_MATCHER_HAMCREST;
import static ru.red.reactiveexaminingplatform.util.UUIDUtils.safeFromString;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@WithMockUser(roles = "ADMIN")
class ExamControllerAdminTest {
    @Value("${examining.tests.db.timeout:5}")
    long DB_TIMEOUT_DURATION_SECONDS;
    @Autowired
    WebTestClient client;

    @Autowired
    ExamRepository examRepository;

    @AfterEach
    void tearDown() {
        examRepository.deleteAll().subscribe();
    }

    @Test
    void createExam() {
        client.post()
                .uri("/api/admin/exam")
                .body(Mono.just(randomExam()), Exam.class)
                .exchange()
                .expectAll(
                        responseSpec -> responseSpec
                                .expectBody(String.class)
                                .value(UUID_MATCHER_HAMCREST)
                                .consumeWith(stringEntityExchangeResult -> {
                                    UUID uuid = safeFromString(stringEntityExchangeResult.getResponseBody());
                                    assertEquals(
                                            Boolean.TRUE,
                                            examRepository
                                                    .existsById(uuid)
                                                    .block(Duration.ofSeconds(DB_TIMEOUT_DURATION_SECONDS)),
                                            "Exam is not persisted OR " +
                                                    "couldn't reach DB within " +
                                                    "Timeout of " + DB_TIMEOUT_DURATION_SECONDS + " seconds"
                                    );
                                }),
                        responseSpec -> responseSpec.expectStatus().isOk()
                );
    }

    @Test
    void modifyExam() {
    }

    @Test
    void deleteExam() {
    }

    @Test
    void fetchExamById() {
    }

    @Test
    void fetchExamByObject() {
    }
}
