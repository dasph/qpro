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

  public TestTemplate () {}
  public TestTemplate (String name) { this.name = name; }

  public Long getId () { return this.id; }

  public String getName () { return this.name; }
  public void setName (String name) { this.name = name; }

  public User getUser () { return this.user; }
}
