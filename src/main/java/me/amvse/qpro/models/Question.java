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
@Table(name = "questions")
public class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name="testTemplateId", nullable=false)
  private TestTemplate testTemplate;

  @Column(name = "image", length = 1500000)
  private String image;

  @Column(name = "value", nullable = false, length = 256)
  private String value;

  public Question () {}
  public Question (TestTemplate testTemplate, String image, String value) {
    this.testTemplate = testTemplate;
    this.image = image;
    this.value = value;
  }

  public Long getId () { return this.id; }

  public TestTemplate getTestTemplate () { return this.testTemplate; }
  public void setTestTemplate (TestTemplate testTemplate) { this.testTemplate = testTemplate; }

  public String getImage () { return this.image; }
  public void setImage (String image) { this.image = image; }

  public String getValue () { return this.value; }
  public void setValue (String value) { this.value = value; }
}
