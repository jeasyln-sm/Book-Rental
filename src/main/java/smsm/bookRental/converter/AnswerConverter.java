package smsm.bookRental.converter;

import smsm.bookRental.dto.AnswerDto;
import smsm.bookRental.entity.Answer;
import smsm.bookRental.entity.Member;
import smsm.bookRental.entity.Question;

import java.time.LocalDateTime;

public class AnswerConverter {

    public static AnswerDto toDto(Answer answer) {
        Long questionId = (answer.getQuestion() != null) ? answer.getQuestion().getId() : null;

        return AnswerDto.builder()
                .id(answer.getId())
                .memberDto(MemberConverter.toDto(answer.getMember()))
                .questionId(answer.getQuestion().getId())
                .content(answer.getContent())
                .createDate(answer.getCreateDate())
                .build();
    }


    public static Answer toEntity(AnswerDto answerDto, Member member, Question question) {

        if(question == null || member == null) {
            throw new IllegalArgumentException("User or Question cannot be null");
        }

        return Answer.builder()
                .member(member)
                .question(question)
                .content(answerDto.getContent())
                .createDate(LocalDateTime.now())
                .build();
    }
}
