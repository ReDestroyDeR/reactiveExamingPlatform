package ru.red.reactiveexaminingplatform.controller.exam;

import com.mongodb.assertions.Assertions;
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

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static ru.red.reactiveexaminingplatform.util.ExamUtils.randomExam;
import static ru.red.reactiveexaminingplatform.util.UUIDUtils.UUID_MATCHER_HAMCREST;
import static ru.red.reactiveexaminingplatform.util.UUIDUtils.safeFromString;
import static ru.red.reactiveexaminingplatform.util.WebTestClientUtils.postInBodyGetExchange;
import static ru.red.reactiveexaminingplatform.util.WebTestClientUtils.postInBodyGetResponse;

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
        postInBodyGetExchange(client, "/api/admin/exam", randomExam())
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
    void tryCreatingSameExam() {
        Exam exam = randomExam();
        postInBodyGetExchange(client, "/api/admin/exam", exam).expectStatus().isOk();
        postInBodyGetExchange(client, "/api/admin/exam", exam).expectStatus().isBadRequest();
    }

    @Test
    void modifyExam() {
        Exam exam = randomExam();
        String title = exam.getTitle();
        UUID uuid = safeFromString(postInBodyGetResponse(client, "/api/admin/exam", exam, String.class));

        exam.setTitle("Patched exam");
        exam.setUuid(uuid);

        client.patch()
                .uri("/api/admin/exam")
                .body(Mono.just(exam), Exam.class)
                .exchange()
                .expectBody(Exam.class)
                .consumeWith(body ->
                        assertNotEquals(
                                requireNonNull(body.getResponseBody()).getTitle(),
                                title
                        )
                );
    }

    @Test
    void deleteExam() {
        String uuid = requireNonNull(postInBodyGetResponse(client, "/api/admin/exam", randomExam(), String.class));

        client.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/admin/exam")
                        .queryParam("uuid", uuid)
                        .build())
                .exchange()
                .expectStatus()
                .isOk();

        examRepository.existsById(UUID.fromString(uuid)).subscribe(Assertions::assertFalse);
    }

    @Test
    void deleteNonExistentExam() {
        String uuid = UUID.randomUUID().toString();

        examRepository.existsById(UUID.fromString(uuid)).subscribe(Assertions::assertFalse);

        client.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/admin/exam")
                        .queryParam("uuid", uuid)
                        .build())
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void fetchExamById() {
        Exam exam = randomExam();
        String uuid = requireNonNull(postInBodyGetResponse(client, "/api/admin/exam", exam, String.class));

        client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/admin/exam")
                        .queryParam("uuid", uuid)
                        .build())
                .exchange()
                .expectAll(
                        responseSpec -> responseSpec
                                .expectStatus()
                                .isOk(),

                        responseSpec -> responseSpec
                                .expectBody(Exam.class)
                                .consumeWith(ex -> assertEquals(exam, ex.getResponseBody()))
                );
    }

    @Test
    void tryFetchingNonExistentExamById() {
        client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/admin/exam")
                        .queryParam("uuid", UUID.randomUUID().toString())
                        .build())
                .exchange()
                .expectAll(
                        responseSpec -> responseSpec
                                .expectStatus()
                                .isNotFound()
                );
    }
}
