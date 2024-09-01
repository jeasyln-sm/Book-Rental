package smsm.bookRental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smsm.bookRental.repository.AnswerRepository;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
}
