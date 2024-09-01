package smsm.bookRental.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import smsm.bookRental.dto.MemberDto;
import smsm.bookRental.entity.Member;

@Mapper(componentModel = "spring")
public abstract class MemberMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    public abstract Member toEntity(MemberDto memberDto);

    public abstract MemberDto toDto(Member member);

    @Named("encodePassword")
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
