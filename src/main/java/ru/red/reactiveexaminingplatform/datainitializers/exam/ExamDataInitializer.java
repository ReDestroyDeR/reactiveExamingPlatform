package ru.red.reactiveexaminingplatform.datainitializers.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.red.reactiveexaminingplatform.datainitializers.DataInitializer;
import ru.red.reactiveexaminingplatform.domain.exam.Exam;
import ru.red.reactiveexaminingplatform.domain.exam.Question;
import ru.red.reactiveexaminingplatform.domain.exam.QuestionType;
import ru.red.reactiveexaminingplatform.repository.exam.ExamRepository;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Order(1)
@Profile("test")
public class ExamDataInitializer implements DataInitializer {
    private final ExamRepository examRepository;

    @Autowired
    public ExamDataInitializer(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public void initialize() {
        Stream.iterate(0, i -> i + 1)
                .limit(10)
                .map(i -> new Exam(
                        "Test Exam " + 1,
                        "Test Exam " + i + " description",
                        Stream.iterate(0, qI -> qI + 1)
                                .limit(new Random(i).nextInt(5, 50))
                                .map(qI -> {
                                    Random random = new Random(qI);
                                    QuestionType type = QuestionType.values()[random.nextInt(0, 2)];
                                    Question question;
                                    switch (type) {
                                        case SELECT -> question = Question.createSelect(
                                                Stream.iterate(0, q -> q + 1)
                                                        .limit(random.nextInt(2, 10))
                                                        .map(q -> "Answer " + q)
                                                        .collect(Collectors.toList())
                                                        .toArray(String[]::new)
                                        );

                                        case WRITE_EXACT -> question = Question.createWriteExact(
                                                String.valueOf(random.nextInt(100))
                                        );

                                        case EXPLAIN -> question = Question.createExplain();

                                        default -> throw new UnsupportedOperationException();
                                    }

                                    return question;
                                })
                                .collect(Collectors.toSet())
                ))
                .forEach(examRepository::save);
    }
}
