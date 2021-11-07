package ru.red.reactiveexaminingplatform.controller.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.red.reactiveexaminingplatform.domain.exam.Exam;
import ru.red.reactiveexaminingplatform.service.exam.ExamService;

@RestController
@RequestMapping("/api/admin/exam")
public class ExamControllerAdmin {
    private final ExamService examService;

    @Autowired
    public ExamControllerAdmin(@Qualifier("examServiceAdmin") ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public Mono<Exam> createExam(@RequestBody Exam exam) {
        return Mono.empty();
    }


}
