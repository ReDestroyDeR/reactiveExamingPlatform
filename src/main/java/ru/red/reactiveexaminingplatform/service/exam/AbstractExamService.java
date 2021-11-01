package ru.red.reactiveexaminingplatform.service.exam;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Exam;
import ru.red.reactiveexaminingplatform.repository.exam.ExamRepository;

import java.util.UUID;

public abstract class AbstractExamService implements ExamService {
    protected final ExamRepository examRepository;

    protected AbstractExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public Flux<Exam> findByTitle(String title) {
        return examRepository.findAllByTitle(title);
    }

    @Override
    public Flux<Exam> findByDescription(String description) {
        return examRepository.findAllByDescription(description);
    }

    @Override
    public Mono<Boolean> exists(UUID uuid) {
        return examRepository.existsById(uuid);
    }

    @Override
    public Mono<Exam> findById(UUID uuid) {
        return examRepository.findById(uuid);
    }

    @Override
    public Flux<Exam> findAll() {
        return examRepository.findAll();
    }
}
