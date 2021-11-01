package ru.red.reactiveexaminingplatform.service.identity;

import ru.red.reactiveexaminingplatform.domain.identity.User;
import ru.red.reactiveexaminingplatform.service.ReactiveCrudService;

import java.util.UUID;

public interface UserService extends ReactiveCrudService<User, UUID> {
}
