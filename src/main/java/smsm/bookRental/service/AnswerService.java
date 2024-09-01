package smsm.bookRental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smsm.bookRental.entity.Answer;
import smsm.bookRental.entity.Member;
import smsm.bookRental.entity.Question;
import smsm.bookRental.repository.AnswerRepository;
import smsm.bookRental.repository.MemberRepository;
import smsm.bookRental.repository.QuestionRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;


    public void create(Question question, Member member, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setMember(member);
        answer.setQuestion(question);
        this.answerRepository.save(answer);
    }
}
