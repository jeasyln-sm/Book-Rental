package smsm.bookRental.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.naming.factory.LookupFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smsm.bookRental.entity.Member;
import smsm.bookRental.repository.MemberRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    private final MemberRepository memberRepository;

    // 회원 등록
    @Transactional
    public Member saveMember(Member member) {
        validateDuplicationMember(member);
        logger.info("새 회원 등록 : {}", member.getEmail());
        return memberRepository.save(member);
    }

    private void validateDuplicationMember(Member member) {
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());
        if(findMember.isPresent()) {
            logger.warn("중복 이메일 등록 시도 : {}", member.getName());
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.debug("이메일로 사용자 로딩 시도 : {}", email);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.warn("이메일로 사용자를 찾을 수 없습니다. : {}", email);
                    return new UsernameNotFoundException("해당 사용자가 없습니다." + email);
                });
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    public boolean isEmailExists(String email) {
        boolean exists = memberRepository.findByEmail(email).isPresent();
        logger.debug("이메일이 있는지 확인 : {} - 결과 : {}", email, exists);
        return exists;
    }
}
