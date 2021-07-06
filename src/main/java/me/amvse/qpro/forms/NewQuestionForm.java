package me.amvse.qpro.forms;

import org.springframework.web.multipart.MultipartFile;

public class NewQuestionForm {
  private MultipartFile image;
  private String value;
  private String imageB64;

  private String answerA;
  private Boolean checkboxA = false;
  private String answerB;
  private Boolean checkboxB = false;
  private String answerC;
  private Boolean checkboxC = false;
  private String answerD;
  private Boolean checkboxD = false;

  public MultipartFile getImage () { return this.image; }
  public void setImage (MultipartFile image) { this.image = image; }

  public String getValue () { return this.value; }
  public void setValue (String value) { this.value = value; }

  public String getAnswerA () { return this.answerA; }
  public void setAnswerA (String answerA) { this.answerA = answerA; }

  public Boolean getCheckboxA () { return this.checkboxA; }
  public void setCheckboxA (Boolean checkboxA) { this.checkboxA = checkboxA; }

  public String getAnswerB () { return this.answerB; }
  public void setAnswerB (String answerB) { this.answerB = answerB; }

  public Boolean getCheckboxB () { return this.checkboxB; }
  public void setCheckboxB (Boolean checkboxB) { this.checkboxB = checkboxB; }

  public String getAnswerC () { return this.answerC; }
  public void setAnswerC (String answerC) { this.answerC = answerC; }

  public Boolean getCheckboxC () { return this.checkboxC; }
  public void setCheckboxC (Boolean checkboxC) { this.checkboxC = checkboxC; }

  public String getAnswerD () { return this.answerD; }
  public void setAnswerD (String answerD) { this.answerD = answerD; }

  public Boolean getCheckboxD () { return this.checkboxD; }
  public void setCheckboxD (Boolean checkboxD) { this.checkboxD = checkboxD; }

  public String getImageB64 () { return this.imageB64; }
  public void setImageB64 (String imageB64) { this.imageB64 = imageB64; }
}
