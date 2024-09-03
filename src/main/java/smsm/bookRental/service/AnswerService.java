package smsm.bookRental.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smsm.bookRental.dto.AnswerDto;
import smsm.bookRental.entity.Answer;
import smsm.bookRental.entity.Member;
import smsm.bookRental.entity.Question;
import smsm.bookRental.excption.DataNotFoundException;
import smsm.bookRental.repository.AnswerRepository;
import smsm.bookRental.repository.QuestionRepository;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;


    // 새 답변 등록
    public void create(Question question, Member member, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setMember(member);
        answer.setQuestion(question);
        this.answerRepository.save(answer);
    }


    // 답변 가져오기
    public Answer getAnswer(Long id) {
        return this.answerRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("해당 답변을 찾을 수 없습니다."));
    }


    // 답변 수정
    @Transactional
    public void update(Long id, String content, Member member) {
        Answer answer = getAnswer(id);

        // 권한 검사
        if (!answer.getMember().equals(member)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    public void delete(Long id) {
        Answer answer = getAnswer(id);
        answerRepository.delete(answer);
    }

}
