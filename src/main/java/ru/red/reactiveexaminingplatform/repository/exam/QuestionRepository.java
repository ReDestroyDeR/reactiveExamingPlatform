package ru.red.reactiveexaminingplatform.repository.exam;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Question;

import java.util.UUID;

public interface QuestionRepository extends ReactiveMongoRepository<Question, UUID> {
    Mono<Question> findByTitle(String title);

    Mono<Question> findByDescription(String description);

    @Query("{\"title\": /.*?0.*/}")
    Flux<Question> findAllByTitle(String title);

    @Query("{\"description\": /.*?0.*/}")
    Flux<Question> findAllByDescription(String description);

    @Query("{\"examId\": ?0")
    Flux<Question> findAllByExamId(UUID examId);
}
