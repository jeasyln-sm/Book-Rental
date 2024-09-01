package smsm.bookRental.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.util.Members;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smsm.bookRental.converter.QuestionConverter;
import smsm.bookRental.dto.MemberDto;
import smsm.bookRental.dto.QuestionDto;
import smsm.bookRental.entity.Member;
import smsm.bookRental.entity.Question;
import smsm.bookRental.excption.DataNotFoundException;
import smsm.bookRental.repository.QuestionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberService memberService;


    public List<QuestionDto> getList() {
    List<Question> questions = this.questionRepository.findAll();
    List<QuestionDto> questionDtos = new ArrayList<>(questions.size());

    for (Question question : questions) {
        QuestionDto dto = new QuestionDto();
        dto.setId(question.getId());
        dto.setSubject(question.getSubject());
        dto.setContent(question.getContent());
        dto.setCreateDate(question.getCreateDate());

        if (question.getMember() != null) {
            MemberDto memberDto = new MemberDto();
            memberDto.setId(question.getMember().getId());
            memberDto.setName(question.getMember().getName());
            memberDto.setEmail(question.getMember().getEmail());
            dto.setMemberDto(memberDto);
        }

        questionDtos.add(dto);
    }

    return questionDtos;
}

    public Question getQuestion(Long id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if(question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }


    @Transactional
    public void create(QuestionDto questionDto, Member member) {
        Question question = Question.builder()
                .subject(questionDto.getSubject())
                .content(questionDto.getContent())
                .createDate(LocalDateTime.now())
                .member(member)
                .build();

        questionRepository.save(question);
    }
}
