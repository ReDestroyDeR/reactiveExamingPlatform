package ru.red.reactiveexaminingplatform.service.exam;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Exam;
import ru.red.reactiveexaminingplatform.domain.exam.Participation;

import java.util.UUID;

@Service("examServiceAdmin")
public class ExamServiceAdmin extends AbstractExamService {
    @Override
    public Mono<Participation> join(UUID userUUID) {
        return null;
    }

    @Override
    public Mono<Participation> submit(UUID userUUID, Participation participation) {
        return null;
    }

    @Override
    public void create(Exam object) {

    }

    @Override
    public Mono<Exam> delete(Exam object) {
        return null;
    }

    @Override
    public Mono<Exam> deleteById(UUID uuid) {
        return null;
    }

    @Override
    public void update(Exam object) {

    }
}
