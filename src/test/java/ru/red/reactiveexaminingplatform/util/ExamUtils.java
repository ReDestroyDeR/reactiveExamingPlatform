package ru.red.reactiveexaminingplatform.util;

import ru.red.reactiveexaminingplatform.domain.exam.Exam;

import java.util.Random;

public final class ExamUtils {
    public static Exam randomExam() {
        Random random = new Random();
        String name = "Random Exam " + random.nextInt(10000);
        String description = name + " description";
        return new Exam(name, description);
    }
}
