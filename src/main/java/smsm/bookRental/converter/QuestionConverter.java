package smsm.bookRental.converter;

import smsm.bookRental.dto.QuestionDto;
import smsm.bookRental.entity.Member;
import smsm.bookRental.entity.Question;

import java.util.stream.Collectors;

public class QuestionConverter {

    public static QuestionDto toDto(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .memberDto(MemberConverter.toDto(question.getMember()))
                .subject(question.getSubject())
                .content(question.getContent())
                .createDate(question.getCreateDate())
                .answerDtos(question.getAnswers().stream()
                        .map(AnswerConverter::toDto)
                        .collect(Collectors.toList())
                        )
                .build();
    }

    public static Question toEntity(QuestionDto questionDto, Member member) {

        if(member == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        return Question.builder()
                .member(member)
                .subject(questionDto.getSubject())
                .content(questionDto.getContent())
                .createDate(questionDto.getCreateDate())
                .build();
    }

}
