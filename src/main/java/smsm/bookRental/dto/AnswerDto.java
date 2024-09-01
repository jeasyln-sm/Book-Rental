package smsm.bookRental.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class AnswerDto {

    private Long id;

    @NotNull(message = "사용자는 비워둘 수 없습니다.")
    private MemberDto memberDto;

    @NotNull(message = "내용은 비워둘 수 없습니다.")
    private String content;

    private LocalDateTime createDate;

    @NotNull(message = "질문은 비워둘 수 없습니다.")
    private Long questionId;

}
