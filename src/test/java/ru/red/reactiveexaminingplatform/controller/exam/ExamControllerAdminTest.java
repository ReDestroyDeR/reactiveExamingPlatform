package ru.red.reactiveexaminingplatform.controller.exam;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Exam;

import static ru.red.reactiveexaminingplatform.util.ExamUtils.randomExam;

@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@WithMockUser(roles = "ADMIN")
class ExamControllerAdminTest {
    @Autowired
    WebTestClient client;

    @Test
    void createExam() {
        client.post()
                .uri("/api/admin/exam")
                .body(Mono.just(randomExam()), Exam.class)
                .exchange()
                .expectStatus().isOk();
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
