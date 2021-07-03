package me.amvse.qpro.repositories;

import me.amvse.qpro.models.AnswerSubmission;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AnswerSubmissionRepository extends JpaRepository<AnswerSubmission, Long> {
}
