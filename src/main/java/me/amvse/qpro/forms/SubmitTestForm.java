package me.amvse.qpro.forms;

import java.util.HashMap;

public class SubmitTestForm {
  private HashMap<Long, Long> answers = new HashMap<>();

  public HashMap<Long, Long> getAnswers () { return this.answers; }
  public void setAnswers (Long aid, Long qid) { this.answers.put(aid, qid); }
}
