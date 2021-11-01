package ru.red.reactiveexaminingplatform.repository.identity;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.identity.User;

import java.util.UUID;

public interface UserRepository extends ReactiveMongoRepository<User, UUID> {
    Mono<User> findByUsername(String username);

    @Query(value = "{'_id' : ?0 }", fields = "{'_id' : 0, 'participationSet' : 1}")
    Mono<User> findUserWithParticipationSetById(UUID uuid);

    @Query(value = "{'username' : ?0 }", fields = "{'_id' : 0, 'participationSet' : 1}")
    Mono<User> findUserWithParticipationSetByUsername(String username);
}
