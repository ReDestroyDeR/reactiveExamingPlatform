package ru.red.reactiveexaminingplatform.service.identity;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.identity.User;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public Mono<User> create(User object) {
        return null;
    }

    @Override
    public Mono<User> save(User object) {
        return null;
    }

    @Override
    public Mono<Void> delete(User object) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(UUID uuid) {
        return null;
    }

    @Override
    public Mono<Boolean> exists(UUID uuid) {
        return null;
    }

    @Override
    public Mono<User> findById(UUID uuid) {
        return null;
    }

    @Override
    public Flux<User> findAll() {
        return null;
    }
}
