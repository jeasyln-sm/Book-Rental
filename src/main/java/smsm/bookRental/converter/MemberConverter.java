package smsm.bookRental.converter;

import org.springframework.security.crypto.password.PasswordEncoder;
import smsm.bookRental.constant.Role;
import smsm.bookRental.dto.MemberDto;
import smsm.bookRental.entity.Member;

public class MemberConverter {

    // Member -> MemberDto
    public static MemberDto toDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .password(member.getPassword())
                .build();
    }


    // MemberDto -> Member
    public static Member toEntity(MemberDto memberDto) {
        return Member.builder()
                .id(memberDto.getId())
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .role(Role.USER)
                .build();
    }


    // MemberFormDto -> Member + password
    public static Member formMemberDto(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .role(Role.USER)
                .build();
    }
}
