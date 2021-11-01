package ru.red.reactiveexaminingplatform.domain.exam;

import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Document
public class Question {
    @Id
    @Setter
    private UUID uuid;

    @Setter
    private String title;

    @Setter
    private String description;

    private QuestionType type;

    @Setter
    private UUID examId;

    @Nullable
    private List<String> selectAnswers;

    @Nullable
    private String exactAnswer;

    private Question() {
        this.uuid = UUID.randomUUID();
    }

    public static Question createSelect(String... answers) {
        Question question = new Question();
        question.type = QuestionType.SELECT;
        question.selectAnswers = List.of(answers);
        return question;
    }

    public static Question createWriteExact(String answer) {
        Question question = new Question();
        question.type = QuestionType.WRITE_EXACT;
        question.exactAnswer = answer;
        return question;
    }

    public static Question createExplain() {
        Question question = new Question();
        question.type = QuestionType.EXPLAIN;
        return question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return uuid.equals(question.uuid)
                && title.equals(question.title)
                && description.equals(question.description)
                && type == question.type
                && examId.equals(question.examId)
                && Objects.equals(selectAnswers, question.selectAnswers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, title, description, type, examId, selectAnswers);
    }
}

