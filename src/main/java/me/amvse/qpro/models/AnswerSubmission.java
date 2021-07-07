package me.amvse.qpro.models;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "answerSubmissions")
public class AnswerSubmission {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name="userId", insertable = false, updatable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name="testId", insertable = false, updatable = false)
  private Test test;

  @ManyToOne
  @JoinColumn(name="questionId", insertable = false, updatable = false)
  private Question question;

  @ManyToOne
  @JoinColumn(name="answerId", insertable = false, updatable = false)
  private Answer answer;

  public AnswerSubmission () {}

  public Long getId () { return this.id; }

  public User getUser () { return this.user; }

  public Test getTest () { return this.test; }

  public Question getQuestion () { return this.question; }

  public Answer getAnswer () { return this.answer; }
}
