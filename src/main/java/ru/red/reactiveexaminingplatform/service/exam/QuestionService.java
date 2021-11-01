package ru.red.reactiveexaminingplatform.service.exam;

import reactor.core.publisher.Flux;
import ru.red.reactiveexaminingplatform.domain.exam.Question;
import ru.red.reactiveexaminingplatform.service.ReactiveCrudService;

import java.util.UUID;

public interface QuestionService extends ReactiveCrudService<Question, UUID> {
    Flux<Question> getAll(UUID examUUID);

    Flux<Question> findByTitle(String title);

    Flux<Question> findByTitleInExam(UUID examUUID, String title);

    Flux<Question> findByDescription(String description);

    Flux<Question> findByDescriptionInExam(UUID examUUID, String description);
}
