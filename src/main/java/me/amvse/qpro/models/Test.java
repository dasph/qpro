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
@Table(name = "tests")
public class Test {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name="testTemplateId", nullable=false)
  private TestTemplate testTemplate;

  @Column(name = "key", nullable = false, length = 16, unique = true)
  private String key;

  public Test () {}

  public Long getId () { return this.id; }
  public TestTemplate getTestTemplate () { return this.testTemplate; }
}
