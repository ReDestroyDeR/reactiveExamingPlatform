package ru.red.reactiveexaminingplatform.service.exam;

import reactor.core.publisher.Flux;
import ru.red.reactiveexaminingplatform.domain.exam.Question;
import ru.red.reactiveexaminingplatform.service.util.ReactiveDeleteService;
import ru.red.reactiveexaminingplatform.service.util.ReactiveReadService;
import ru.red.reactiveexaminingplatform.service.util.ReactiveUpdateService;

import java.util.UUID;

public interface QuestionService extends ReactiveReadService<Question, UUID>,
                                        ReactiveUpdateService<Question, UUID>,
                                        ReactiveDeleteService<Question, UUID> {
    void create(Question question, UUID examUUID);

    Flux<Question> getAll(UUID examUUID);

    Flux<Question> findByTitle(String title);

    Flux<Question> findByTitleInExam(UUID examUUID, String title);

    Flux<Question> findByDescription(String description);

    Flux<Question> findByDescriptionInExam(UUID examUUID, String description);
}
