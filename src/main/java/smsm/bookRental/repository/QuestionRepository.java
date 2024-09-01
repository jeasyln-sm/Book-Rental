package smsm.bookRental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smsm.bookRental.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
