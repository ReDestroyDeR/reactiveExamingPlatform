package ru.red.reactiveexaminingplatform.service.util;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveCrudService<T, ID> {
    void save(T object);

    Mono<T> delete(T object);

    Mono<T> deleteById(ID id);

    Mono<Boolean> exists(ID id);

    Mono<T> get(ID id);

    Flux<T> getAll();
}
