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
@Table(name = "testTemplates")
public class TestTemplate {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "name", nullable = false, length = 128)
  private String name;

  @ManyToOne
  @JoinColumn(name = "userId", insertable = false, updatable = false)
  private User user;

  @OneToMany
  @JoinColumn(name = "testTemplateId")
  @OrderBy("id ASC")
  private List<Question> questions = new ArrayList<>();

  @OneToMany
  @JoinColumn(name = "testTemplateId")
  @OrderBy("id ASC")
  private List<Test> tests = new ArrayList<>();

  public TestTemplate () {}
  public TestTemplate (String name) { this.name = name; }

  public Long getId () { return this.id; }

  public String getName () { return this.name; }
  public void setName (String name) { this.name = name; }

  public User getUser () { return this.user; }

  public List<Question> getQuestions () { return this.questions; }
  public void addQuestion (Question questions) { this.questions.add(questions); }

  public List<Test> getTests () { return this.tests; }
  public void addTest (Test test) { this.tests.add(test); }
}
