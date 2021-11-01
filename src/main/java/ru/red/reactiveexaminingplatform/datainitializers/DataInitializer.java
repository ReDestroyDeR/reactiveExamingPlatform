package ru.red.reactiveexaminingplatform.datainitializers;

import org.springframework.context.annotation.Profile;

@Profile("test")
public interface DataInitializer {
    void initialize();
}
