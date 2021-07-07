package me.amvse.qpro.repositories;

import me.amvse.qpro.models.AnswerSubmission;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AnswerSubmissionRepository extends JpaRepository<AnswerSubmission, Long> {
  List<AnswerSubmission> findByUserIdAndTestId (Long userId, Long testId);
  List<AnswerSubmission> findByTestId (Long testId);
  void deleteByUserIdAndTestId (Long userId, Long testId);
}
