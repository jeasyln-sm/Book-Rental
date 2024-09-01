package smsm.bookRental.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    private Long id;

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 4, max = 12, message = "비밀번호는 최소 4자, 최대 12자를 입력해주세요.")
    private String password;
}
