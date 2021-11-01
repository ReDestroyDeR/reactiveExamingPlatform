package ru.red.reactiveexaminingplatform.service.exam;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Exam;
import ru.red.reactiveexaminingplatform.domain.exam.Participation;
import ru.red.reactiveexaminingplatform.service.ReactiveCrudService;

import java.util.UUID;

public interface ExamService extends ReactiveCrudService<Exam, UUID> {
    Flux<Exam> findByTitle(String title);

    Flux<Exam> findByDescription(String description);

    Mono<Participation> join(UUID userUUID, UUID examUUID);

    Mono<Participation> submit(UUID userUUID, Participation participation);
}
