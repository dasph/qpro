package me.amvse.qpro.models;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "questions")
public class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "testTemplateId", insertable = false, updatable = false)
  private TestTemplate testTemplate;

  @Column(name = "image", columnDefinition = "TEXT")
  private String image;

  @Column(name = "value", nullable = false, length = 512)
  private String value;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "questionId")
  @OrderBy("id ASC")
  private List<Answer> answers = new ArrayList<>();

  @OneToMany
  @JoinColumn(name = "questionId")
  @OrderBy("id ASC")
  private List<AnswerSubmission> answerSumbissions = new ArrayList<>();

  public Question () {}
  public Question (String value, String image) {
    this.value = value;
    this.image = image;
  }

  public Long getId () { return this.id; }

  public TestTemplate getTestTemplate () { return this.testTemplate; }

  public String getImage () { return this.image; }
  public void setImage (String image) { this.image = image; }

  public String getValue () { return this.value; }
  public void setValue (String value) { this.value = value; }

  public List<Answer> getAnswers () { return this.answers; }
  public void addAnswer (Answer answer) { this.answers.add(answer); }

  public List<AnswerSubmission> getAnswerSubmissions () { return this.answerSumbissions; }
  public void addAnswerSubmission (AnswerSubmission answerSubmission) { this.answerSumbissions.add(answerSubmission); }
}
