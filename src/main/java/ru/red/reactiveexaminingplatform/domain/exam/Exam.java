package ru.red.reactiveexaminingplatform.domain.exam;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Getter
@Setter
@Document
public class Exam {
    @Id
    private UUID uuid;
    private String title;
    private String description;
    private Set<Question> questionSet;

    public Exam() {
        this("");
    }

    public Exam(String title) {
        this(title, "");
    }

    public Exam(String title, String description) {
        this(title, description, new HashSet<>());
    }

    public Exam(String title, String description, Set<Question> questionSet) {
        this.uuid = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.questionSet = questionSet;
        this.questionSet.forEach(q -> q.setExamId(this.uuid));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return uuid.equals(exam.uuid)
                && title.equals(exam.title)
                && description.equals(exam.description)
                && questionSet.equals(exam.questionSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, title, description, questionSet);
    }
}
