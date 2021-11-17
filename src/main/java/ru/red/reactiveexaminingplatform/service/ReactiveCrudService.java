package ru.red.reactiveexaminingplatform.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveCrudService<T, ID> {
    Mono<T> create(T object);

    Mono<T> save(T object);

    Mono<Void> delete(T object);

    Mono<Void> deleteById(ID id);

    Mono<Boolean> exists(ID id);

    Mono<T> findById(ID id);

    Flux<T> findAll();
}
