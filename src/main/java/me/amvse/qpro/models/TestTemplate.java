package me.amvse.qpro.models;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "testTemplates")
public class TestTemplate {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name="authorId", nullable=false)
  private User author;

  public TestTemplate () {}

  public Long getId () { return this.id; }
  public User getAuthor () { return this.author; }
}
