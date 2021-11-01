package ru.red.reactiveexaminingplatform.repository.exam;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Question;

import java.util.UUID;

public interface QuestionRepository extends ReactiveMongoRepository<Question, UUID> {
    Mono<Question> findByTitle(String title);

    Mono<Question> findByDescription(String description);

    @Query("{\"title\": /.*:title.*/}")
    Flux<Question> findAllByTitle(@Param("title") String title);

    @Query("{\"description\": /.*:description.*/}")
    Flux<Question> findAllByDescription(@Param("description") String description);

    @Query("{\"examId\": :examId")
    Flux<Question> findAllByExamId(@Param("examId") UUID examId);
}
