package ru.red.reactiveexaminingplatform.service.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Participation;
import ru.red.reactiveexaminingplatform.domain.exam.Question;
import ru.red.reactiveexaminingplatform.service.identity.UserService;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParticipationServiceUser implements ParticipationService {
    private final ExamService examService;
    private final UserService userService;

    @Autowired
    public ParticipationServiceUser(@Qualifier("examServiceUser") ExamService examService, UserService userService) {
        this.examService = examService;
        this.userService = userService;
    }

    @Override
    public Mono<Participation> join(UUID userUUID, UUID examUUID) {
        return Mono.just(new Participation(examUUID))
                .doOnNext(p -> {
                    p.setUserUUID(userUUID);
                    examService.findById(examUUID)
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
    public Mono<Participation> submit(Participation participation) {
        return userService.findById(participation.getUuid())
                .map(user -> {
                    participation.setFinished(true);

                    Set<Participation> participationSet = user.getParticipationSet();

                    if (participationSet.stream()
                            .dropWhile(p -> !p.equals(participation))
                            .findFirst()
                            .orElseThrow()
                            .isFinished())
                        throw new IllegalStateException("Participation is already finished");

                    participationSet.add(participation);
                    userService.save(user);

                    return participation;
                });
    }
}
