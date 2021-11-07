package ru.red.reactiveexaminingplatform.service.exam;

import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Participation;
import ru.red.reactiveexaminingplatform.domain.identity.User;

import java.util.UUID;

public interface ParticipationService {
    Mono<Participation> join(UUID userUUID, UUID examUUID);

    Mono<Participation> save(User user, Participation participation);

    Mono<Participation> submit(User user, Participation participation);
}
