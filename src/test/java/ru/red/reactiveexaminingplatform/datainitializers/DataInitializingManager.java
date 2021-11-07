package ru.red.reactiveexaminingplatform.datainitializers;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("data-initialize")
@Component
public class DataInitializingManager {
    public DataInitializingManager(List<DataInitializer> initializers) {
        initializers.forEach(DataInitializer::initialize);
    }
}
