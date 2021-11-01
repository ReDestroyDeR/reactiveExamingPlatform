package ru.red.reactiveexaminingplatform.service.exam;

import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Participation;

import java.util.UUID;

public interface ParticipationService {
    Mono<Participation> join(UUID userUUID, UUID examUUID);

    Mono<Participation> submit(Participation participation);
}
