package ru.red.reactiveexaminingplatform.service.exam;

import reactor.core.publisher.Flux;
import ru.red.reactiveexaminingplatform.domain.exam.Exam;
import ru.red.reactiveexaminingplatform.service.ReactiveCrudService;

import java.util.UUID;

public interface ExamService extends ReactiveCrudService<Exam, UUID> {
    Flux<Exam> findByTitle(String title);

    Flux<Exam> findByDescription(String description);
}
