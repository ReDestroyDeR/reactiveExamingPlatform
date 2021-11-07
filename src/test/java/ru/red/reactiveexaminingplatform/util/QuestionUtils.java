package ru.red.reactiveexaminingplatform.util;

import ru.red.reactiveexaminingplatform.domain.exam.Question;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.red.reactiveexaminingplatform.domain.exam.Question.*;

public final class QuestionUtils {
    public static Question randomSelect() {
        return createSelect(
                Stream.iterate(0, q -> q + 1)
                        .limit(new Random().nextInt(2, 10))
                        .map(q -> "Answer " + q)
                        .collect(Collectors.toList())
                        .toArray(String[]::new));
    }

    public static Question randomWriteExact() {
        return createWriteExact(String.valueOf(new Random().nextInt(100)));
    }

    public static Question randomExplain() {
        return createExplain();
    }
}
