package ru.red.reactiveexaminingplatform.datainitializers;

import org.springframework.context.annotation.Profile;

@Profile("data-initialize")
public interface DataInitializer {
    void initialize();
}
