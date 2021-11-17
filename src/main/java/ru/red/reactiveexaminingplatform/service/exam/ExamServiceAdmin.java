package ru.red.reactiveexaminingplatform.service.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Exam;
import ru.red.reactiveexaminingplatform.exception.BadRequest;
import ru.red.reactiveexaminingplatform.exception.NotFound;
import ru.red.reactiveexaminingplatform.repository.exam.ExamRepository;

import java.util.UUID;

@Service("examServiceAdmin")
public class ExamServiceAdmin extends AbstractExamService {
    @Autowired
    public ExamServiceAdmin(ExamRepository examRepository) {
        super(examRepository);
    }

    @Override
    public Mono<Exam> create(Exam exam) {
        UUID uuid = exam.getUuid();
        return uuid != null
                ? examRepository.findById(uuid)
                .hasElement()
                .flatMap(b -> b
                        ? Mono.error(BadRequest::new)
                        : save(exam))
                : save(exam);
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
        return examRepository.deleteByUuidReturningDeletedCount(uuid)
                .flatMap(count -> count == 0
                        ? Mono.error(new NotFound())
                        : Mono.empty());
    }
}
