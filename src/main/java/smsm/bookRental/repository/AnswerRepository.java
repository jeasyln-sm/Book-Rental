package smsm.bookRental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smsm.bookRental.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
