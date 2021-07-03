package me.amvse.qpro.repositories;

import me.amvse.qpro.models.Question;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
