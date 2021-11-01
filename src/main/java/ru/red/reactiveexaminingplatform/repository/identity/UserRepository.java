package ru.red.reactiveexaminingplatform.repository.identity;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Participation;
import ru.red.reactiveexaminingplatform.domain.identity.User;

import java.util.UUID;

public interface UserRepository extends ReactiveMongoRepository<User, UUID> {
    Mono<User> findByUsername(String username);

    @Query("{}")
    Flux<Participation> getUserParticipationSet(UUID uuid);
    Flux<Participation> getUserParticipationSet(String username);
}
