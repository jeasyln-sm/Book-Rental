package smsm.bookRental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smsm.bookRental.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
