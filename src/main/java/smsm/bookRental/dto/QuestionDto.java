package smsm.bookRental.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class QuestionDto {

    private Long id;

    @Valid
    private MemberDto memberDto;

    @NotBlank(message = "제목은 비워둘 수 없습니다.")
    private String subject;

    @NotBlank(message = "내용은 비워둘 수 없습니다.")
    private String content;

    private LocalDateTime createDate;

    private List<AnswerDto> answerDtos;

}
