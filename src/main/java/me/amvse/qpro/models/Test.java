package me.amvse.qpro.models;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "tests")
public class Test {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name="userId", insertable = false, updatable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name="testTemplateId", insertable = false, updatable = false)
  private TestTemplate testTemplate;

  @OneToMany
  @JoinColumn(name = "testId")
  @OrderBy("id ASC")
  private List<AnswerSubmission> answerSumbissions = new ArrayList<>();

  @Column(name = "key", nullable = false, length = 16, unique = true)
  private String key;

  @Column(name = "active", nullable = false)
  private Boolean active;

  public Test () { this.active = true; }
  public Test (String key, Boolean active) { this.key = key; this.active = active; }

  public Long getId () { return this.id; }
  public User getUser () { return this.user; }
  public TestTemplate getTestTemplate () { return this.testTemplate; }

  public String getKey () { return this.key; }
  public void setKey (String key) { this.key = key; }

  public Boolean getActive () { return this.active; }
  public void setActive (Boolean active) { this.active = active; }

  public List<AnswerSubmission> getAnswerSubmissions () { return this.answerSumbissions; }
  public void addAnswerSubmission (AnswerSubmission answerSubmission) { this.answerSumbissions.add(answerSubmission); }
}
