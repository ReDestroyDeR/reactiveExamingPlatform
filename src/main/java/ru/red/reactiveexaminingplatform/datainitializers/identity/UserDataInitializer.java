package ru.red.reactiveexaminingplatform.datainitializers.identity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.red.reactiveexaminingplatform.datainitializers.DataInitializer;
import ru.red.reactiveexaminingplatform.domain.exam.Exam;
import ru.red.reactiveexaminingplatform.domain.exam.Participation;
import ru.red.reactiveexaminingplatform.domain.identity.Authority;
import ru.red.reactiveexaminingplatform.domain.identity.User;
import ru.red.reactiveexaminingplatform.repository.exam.ExamRepository;
import ru.red.reactiveexaminingplatform.repository.identity.UserRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Order(100)
@Component
@Profile("test")
public class UserDataInitializer implements DataInitializer {
    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, ExamRepository examRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.examRepository = examRepository;
    }

    @Override
    public void initialize() {
        Set<Authority> user = Set.of(new Authority("USER"));
        Set<Authority> admin = Set.of(new Authority("ADMIN"));
        List<Exam> exams = examRepository.findAll().buffer().blockFirst();
        assert exams != null;
        assert exams.size() == 10;

        Stream.concat(
                        Stream.iterate(0, i -> i + 1).limit(100)
                                .map(i -> User.builder()
                                        .username("User" + i)
                                        .password(passwordEncoder.encode("pass" + i))
                                        .authorities(user)
                                        .participationSet(generateParticipationSet(i, exams))
                                        .build()
                                ).peek(u -> u.getParticipationSet()
                                        .forEach(participation -> participation.setUserUUID(u.getUuid()))),
                        Stream.iterate(0, i -> i + 1).limit(10)
                                .map(i -> User.builder()
                                        .username("Admin" + i)
                                        .password(passwordEncoder.encode("pass" + i))
                                        .authorities(admin)
                                        .build()
                                ))
                .forEach(userRepository::save);
    }

    private Set<Participation> generateParticipationSet(int i, List<Exam> exams) {
        return Stream.iterate(0, x -> x + 1).limit(new Random(i).nextInt(10))
                .map(x -> {
                    Exam exam = exams.get(x);
                    Random random = new Random(x);
                    Participation participation = new Participation(
                            exam.getUuid(),
                            random.nextInt(-1, 2)
                    );

                    Map<UUID, String> answers = participation.getAnswers();
                    exam.getQuestionSet().forEach(question ->
                            {
                                String answer;
                                switch (question.getType()) {
                                    case SELECT -> {
                                        List<String> selectAnswers = question.getSelectAnswers();
                                        assert selectAnswers != null;
                                        answer = selectAnswers.get(random.nextInt(selectAnswers.size()));
                                    }

                                    case WRITE_EXACT -> answer = "" + random.nextInt(0, 100);

                                    case EXPLAIN -> answer = random.ints()
                                            .limit(random.nextInt(10))
                                            .mapToObj(String::valueOf)
                                            .collect(Collectors.joining());

                                    default -> throw new UnsupportedOperationException();
                                }
                                answers.put(question.getUuid(), answer);
                            }
                    );
                    participation.setFinished(random.nextBoolean());
                    return participation;
                })
                .collect(Collectors.toSet());
    }
}
