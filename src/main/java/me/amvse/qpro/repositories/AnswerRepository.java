package me.amvse.qpro.repositories;

import me.amvse.qpro.models.Answer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
