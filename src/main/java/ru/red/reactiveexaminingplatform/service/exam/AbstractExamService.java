package ru.red.reactiveexaminingplatform.service.exam;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Exam;

import java.util.UUID;

public abstract class AbstractExamService implements ExamService {
    @Override
    public Flux<Exam> findByTitle(String title) {
        return null;
    }

    @Override
    public Flux<Exam> findByDescription(String description) {
        return null;
    }

    @Override
    public boolean exists(UUID uuid) {
        return false;
    }

    @Override
    public Mono<Exam> get(UUID uuid) {
        return null;
    }

    @Override
    public Flux<Exam> getAll() {
        return null;
    }
}
