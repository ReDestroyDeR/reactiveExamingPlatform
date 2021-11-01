package ru.red.reactiveexaminingplatform.service.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Exam;
import ru.red.reactiveexaminingplatform.domain.exam.Participation;
import ru.red.reactiveexaminingplatform.repository.exam.ExamRepository;

import java.util.UUID;

@Service("examServiceAdmin")
public class ExamServiceAdmin extends AbstractExamService {
    @Autowired
    public ExamServiceAdmin(ExamRepository examRepository) {
        super(examRepository);
    }

    @Override
    public Mono<Participation> join(UUID userUUID, UUID examUUID) {
        return Mono.error(new UnsupportedOperationException("Admin can't participate in exams"));
    }

    @Override
    public Mono<Participation> submit(UUID userUUID, Participation participation) {
        return Mono.error(new UnsupportedOperationException("Admin can't participate in exams"));
    }

    @Override
    public Mono<Exam> save(Exam exam) {
        return examRepository.save(exam);
    }

    @Override
    public Mono<Void> delete(Exam exam) {
        return examRepository.delete(exam);
    }

    @Override
    public Mono<Void> deleteById(UUID uuid) {
        return examRepository.deleteById(uuid);
    }
}
