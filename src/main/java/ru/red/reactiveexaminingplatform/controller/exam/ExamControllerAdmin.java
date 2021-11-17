package ru.red.reactiveexaminingplatform.controller.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Exam;
import ru.red.reactiveexaminingplatform.service.exam.ExamService;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/exam")
public class ExamControllerAdmin {
    private final ExamService examService;

    @Autowired
    public ExamControllerAdmin(@Qualifier("examServiceAdmin") ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public Mono<String> createExam(@RequestBody Exam exam) {
        return examService.create(exam).map(Exam::getUuid).map(UUID::toString);
    }

    @PatchMapping
    public Mono<Exam> patchExam(@RequestBody Exam exam) {
        if (exam.getUuid() == null)
            return Mono.error(new IllegalArgumentException("Provided Exam doesn't have UUID"));

        return examService.save(exam);
    }

    @DeleteMapping
    public Mono<Void> deleteExam(@RequestParam("uuid") String uuid) {
        return examService.deleteById(UUID.fromString(uuid));
    }

    @GetMapping
    public Mono<Exam> findExamByUUID(@RequestParam("uuid") String uuid) {
        return examService.findById(UUID.fromString(uuid));
    }
}
