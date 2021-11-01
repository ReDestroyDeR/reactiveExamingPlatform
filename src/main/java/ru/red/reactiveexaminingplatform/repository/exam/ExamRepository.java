package ru.red.reactiveexaminingplatform.repository.exam;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Exam;

import java.util.UUID;

public interface ExamRepository extends ReactiveMongoRepository<Exam, UUID> {
    Mono<Exam> findByTitle(String title);

    Mono<Exam> findByDescription(String description);

    @Query("{\"title\": /.*:title.*/}")
    Flux<Exam> findAllByTitle(@Param("title") String title);

    @Query("{\"description\": /.*:description.*/}")
    Flux<Exam> findAllByDescription(@Param("description") String description);
}
