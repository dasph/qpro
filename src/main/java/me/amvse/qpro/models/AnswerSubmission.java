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
  @JoinColumn(name="userId", nullable=false)
  private User user;

  @ManyToOne
  @JoinColumn(name="questionId", nullable=false)
  private Question question;

  @ManyToOne
  @JoinColumn(name="answerId", nullable=false)
  private Answer answer;

  public AnswerSubmission () {}
  public AnswerSubmission (Question question, Answer answer) {
    this.question = question;
    this.answer = answer;
  }

  public Long getId () { return this.id; }

  public User getUser () { return this.user; }
  public void setUser (User user) { this.user = user; }

  public Question getQuestion () { return this.question; }
  public void setQuestion (Question question) { this.question = question; }

  public Answer getAnswer () { return this.answer; }
  public void setAnswer (Answer answer) { this.answer = answer; }
}
