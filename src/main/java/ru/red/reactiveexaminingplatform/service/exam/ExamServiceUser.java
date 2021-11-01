package ru.red.reactiveexaminingplatform.service.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Exam;
import ru.red.reactiveexaminingplatform.repository.exam.ExamRepository;

import java.util.UUID;

@Service("examServiceUser")
public class ExamServiceUser extends AbstractExamService {
    @Autowired
    public ExamServiceUser(ExamRepository examRepository) {
        super(examRepository);
    }

    @Override
    public Mono<Exam> save(Exam exam) {
        return Mono.error(new UnsupportedOperationException("User can't manipulate exams"));
    }

    @Override
    public Mono<Void> delete(Exam exam) {
        return Mono.error(new UnsupportedOperationException("User can't manipulate exams"));
    }

    @Override
    public Mono<Void> deleteById(UUID uuid) {
        return Mono.error(new UnsupportedOperationException("User can't manipulate exams"));
    }
}
