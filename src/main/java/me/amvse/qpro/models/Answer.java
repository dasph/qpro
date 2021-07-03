package me.amvse.qpro.models;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "answers")
public class Answer {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name="questionId", nullable=false)
  private Question question;

  @Column(name = "value", nullable = false, length = 256)
  private String value;

  public Answer () {}
  public Answer (Question question, String value) {
    this.question = question;
    this.value = value;
  }

  public Long getId () { return this.id; }

  public Question getQuestion () { return this.question; }
  public void setQuestion (Question question) { this.question = question; }

  public String getValue () { return this.value; }
  public void setValue (String value) { this.value = value; }
}
