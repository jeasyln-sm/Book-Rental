package smsm.bookRental.converter;

import org.springframework.security.crypto.password.PasswordEncoder;
import smsm.bookRental.constant.Role;
import smsm.bookRental.dto.MemberDto;
import smsm.bookRental.entity.Member;

public class MemberConverter {

    // Member -> MemberDto
    public static MemberDto toDto(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }

        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .password(member.getPassword()) // 비밀번호는 보통 DTO에서 제외합니다.
                .build();
    }

    // MemberDto -> Member
    public static Member toEntity(MemberDto memberDto) {
        if (memberDto == null) {
            throw new IllegalArgumentException("MemberDto cannot be null");
        }

        return Member.builder()
                .id(memberDto.getId())
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .password(memberDto.getPassword()) // 비밀번호는 평문으로 받아야 합니다.
                .role(Role.USER) // 기본 역할을 USER로 설정합니다.
                .build();
    }

    // MemberFormDto -> Member + password
    public static Member formMemberDto(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        if (memberDto == null) {
            throw new IllegalArgumentException("MemberDto cannot be null");
        }
        if (passwordEncoder == null) {
            throw new IllegalArgumentException("PasswordEncoder cannot be null");
        }

        return Member.builder()
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .role(Role.USER) // 기본 역할을 USER로 설정합니다.
                .build();
    }
}
