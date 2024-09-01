package smsm.bookRental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import smsm.bookRental.service.AnswerService;

@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final AnswerService answerService;
}
