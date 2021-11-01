package ru.red.reactiveexaminingplatform.service.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Exam;
import ru.red.reactiveexaminingplatform.domain.exam.Participation;
import ru.red.reactiveexaminingplatform.domain.exam.Question;
import ru.red.reactiveexaminingplatform.repository.exam.ExamRepository;

import java.util.UUID;
import java.util.stream.Collectors;

@Service("examServiceUser")
public class ExamServiceUser extends AbstractExamService {
    @Autowired
    public ExamServiceUser(ExamRepository examRepository) {
        super(examRepository);
    }

    @Override
    public Mono<Participation> join(UUID userUUID, UUID examUUID) {
        return Mono.just(new Participation(examUUID))
                .doOnNext(p -> {
                    p.setUserUUID(userUUID);
                    this.findById(examUUID)
                            .subscribe(exam ->
                                    p.getAnswers()
                                            .putAll(exam.getQuestionSet()
                                                    .stream()
                                                    .collect(Collectors.toMap(Question::getUuid, question -> ""))
                                    )
                            );
                });
    }

    @Override
    public Mono<Participation> submit(UUID userUUID, Participation participation) {
        return null;
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
