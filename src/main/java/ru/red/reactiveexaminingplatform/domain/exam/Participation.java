package ru.red.reactiveexaminingplatform.domain.exam;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Document
public class Participation {
    @Id
    private UUID uuid;
    private UUID userUUID;
    private final UUID examUUID;
    private final long examSeed;
    private final Map<UUID, String> answers;
    private boolean finished;

    {
        this.answers = new HashMap<>();
    }

    public Participation(UUID examUUID) {
        this.examUUID = examUUID;
        this.examSeed = -1; // Question order randomizing is disabled
    }

    public Participation(UUID examUUID, long examSeed) {
        this.examUUID = examUUID;
        this.examSeed = examSeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participation that = (Participation) o;
        return uuid.equals(that.uuid)
                && examSeed == that.examSeed
                && finished == that.finished
                && examUUID.equals(that.examUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, examUUID, examSeed, finished);
    }
}
