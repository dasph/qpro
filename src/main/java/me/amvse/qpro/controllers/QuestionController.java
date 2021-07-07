package me.amvse.qpro.controllers;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import me.amvse.qpro.forms.NewQuestionForm;
import me.amvse.qpro.models.Answer;
import me.amvse.qpro.models.Question;
import me.amvse.qpro.models.TestTemplate;
import me.amvse.qpro.models.User;
import me.amvse.qpro.repositories.AnswerRepository;
import me.amvse.qpro.repositories.QuestionRepository;
import me.amvse.qpro.repositories.TestTemplateRepository;
import me.amvse.qpro.repositories.UserRepository;
import me.amvse.qpro.service.SecurityService;
import me.amvse.qpro.validators.NewQuestionFormValidator;

@Controller
public class QuestionController {
  @Autowired
  private SecurityService securityService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TestTemplateRepository testTemplateRepository;

  @Autowired
  private NewQuestionFormValidator newQuestionFormValidator;

  @Autowired
  private QuestionRepository questionRepository;

  @Autowired
  private AnswerRepository answerRepository;

  @GetMapping("/templates/{templateId}/new")
  public String getNewQuestion (@PathVariable Long templateId, Model model) {
    model.addAttribute("view", "addQuestion");
    model.addAttribute("templateId", templateId);
    model.addAttribute("newQuestionForm", new NewQuestionForm());

    return "index";
  }

  @PostMapping("/templates/{templateId}/new")
  public String postNewQuestion (@ModelAttribute("newQuestionForm") NewQuestionForm newQuestionForm, BindingResult bindingResult, @PathVariable Long templateId, Model model) {
    User user = userRepository.findOneByEmail(securityService.getEmail()).orElse(null);
    if (user == null) {
      // throw 404
      return "redirect:/";
    }

    TestTemplate tt = testTemplateRepository.findById(templateId).orElse(null);
    if (tt == null) {
      // throw 404
      return "redirect:/templates";
    }

    if (!tt.getUser().equals(user)) {
      // throw 403
      return "redirect:/templates";
    }

    newQuestionFormValidator.validate(newQuestionForm, bindingResult);
    if (bindingResult.hasErrors()) {
      model.addAttribute("view", "addQuestion");
      return "index";
    }

    String image = null;

    if (!newQuestionForm.getImage().isEmpty()) {
      try {
        byte[] imageBin = newQuestionForm.getImage().getBytes();
        byte[] encoded = Base64.getEncoder().encode(imageBin);

        image = new String(encoded);
      } catch (Throwable e) {}
    }

    Answer answerA = new Answer(newQuestionForm.getAnswerA(), newQuestionForm.getCheckboxA());
    Answer answerB = new Answer(newQuestionForm.getAnswerB(), newQuestionForm.getCheckboxB());
    Answer answerC = new Answer(newQuestionForm.getAnswerC(), newQuestionForm.getCheckboxC());
    Answer answerD = new Answer(newQuestionForm.getAnswerD(), newQuestionForm.getCheckboxD());

    Question question = new Question(newQuestionForm.getValue(), image);

    tt.addQuestion(question);
    questionRepository.save(question);

    question.addAnswer(answerA);
    question.addAnswer(answerB);
    question.addAnswer(answerC);
    question.addAnswer(answerD);

    answerRepository.save(answerA);
    answerRepository.save(answerB);
    answerRepository.save(answerC);
    answerRepository.save(answerD);


    return "redirect:/templates/" + templateId;
  }

  @GetMapping("/templates/{templateId}/{questionId}")
  public String getEditQuestion (@PathVariable Long templateId, @PathVariable Long questionId, Model model) {
    User user = userRepository.findOneByEmail(securityService.getEmail()).orElse(null);
    if (user == null) {
      // throw 404
      return "redirect:/";
    }

    TestTemplate tt = testTemplateRepository.findById(templateId).orElse(null);
    if (tt == null) {
      // throw 404
      return "redirect:/templates/" + templateId;
    }

    if (!tt.getUser().equals(user)) {
      // throw 403
      return "redirect:/templates/" + templateId;
    }

    Question q = questionRepository.findById(questionId).orElse(null);
    if (q == null) {
      // throw 404
      return "redirect:/templates/" + templateId;
    }

    if (!q.getTestTemplate().equals(tt)) {
      // throw 403
      return "redirect:/templates/" + templateId;
    }


    NewQuestionForm nqf = new NewQuestionForm();
    List<Answer> ans = q.getAnswers();

    nqf.setValue(q.getValue());

    try { nqf.setAnswerA(ans.get(0).getValue()); } catch (Throwable e) {}
    try { nqf.setAnswerB(ans.get(1).getValue()); } catch (Throwable e) {}
    try { nqf.setAnswerC(ans.get(2).getValue()); } catch (Throwable e) {}
    try { nqf.setAnswerD(ans.get(3).getValue()); } catch (Throwable e) {}

    try { nqf.setCheckboxA(ans.get(0).getCorrect()); } catch (Throwable e) {}
    try { nqf.setCheckboxB(ans.get(1).getCorrect()); } catch (Throwable e) {}
    try { nqf.setCheckboxC(ans.get(2).getCorrect()); } catch (Throwable e) {}
    try { nqf.setCheckboxD(ans.get(3).getCorrect()); } catch (Throwable e) {}

    nqf.setImageB64(q.getImage());

    model.addAttribute("view", "editQuestion");
    model.addAttribute("templateId", templateId);
    model.addAttribute("questionId", questionId);
    model.addAttribute("newQuestionForm", nqf);

    return "index";
  }

  @PostMapping("/templates/{templateId}/{questionId}")
  public String postEditQuestion (@ModelAttribute("newQuestionForm") NewQuestionForm newQuestionForm, BindingResult bindingResult, @PathVariable Long templateId,  @PathVariable Long questionId, Model model) {
    User user = userRepository.findOneByEmail(securityService.getEmail()).orElse(null);
    if (user == null) {
      // throw 404
      return "redirect:/";
    }

    TestTemplate tt = testTemplateRepository.findById(templateId).orElse(null);
    if (tt == null) {
      // throw 404
      return "redirect:/templates";
    }

    if (!tt.getUser().equals(user)) {
      // throw 403
      return "redirect:/templates";
    }

    Question q = questionRepository.findById(questionId).orElse(null);
    if (q == null) {
      // throw 404
      return "redirect:/templates/" + templateId;
    }

    if (!q.getTestTemplate().equals(tt)) {
      // throw 403
      return "redirect:/templates/" + templateId;
    }

    newQuestionFormValidator.validate(newQuestionForm, bindingResult);
    if (bindingResult.hasErrors()) {
      NewQuestionForm nqf = new NewQuestionForm();
      List<Answer> ans = q.getAnswers();

      nqf.setValue(q.getValue());

      try { nqf.setAnswerA(ans.get(0).getValue()); } catch (Throwable e) {}
      try { nqf.setAnswerB(ans.get(1).getValue()); } catch (Throwable e) {}
      try { nqf.setAnswerC(ans.get(2).getValue()); } catch (Throwable e) {}
      try { nqf.setAnswerD(ans.get(3).getValue()); } catch (Throwable e) {}

      try { nqf.setCheckboxA(ans.get(0).getCorrect()); } catch (Throwable e) {}
      try { nqf.setCheckboxB(ans.get(1).getCorrect()); } catch (Throwable e) {}
      try { nqf.setCheckboxC(ans.get(2).getCorrect()); } catch (Throwable e) {}
      try { nqf.setCheckboxD(ans.get(3).getCorrect()); } catch (Throwable e) {}

      nqf.setImageB64(q.getImage());

      model.addAttribute("view", "editQuestion");
      model.addAttribute("templateId", templateId);
      model.addAttribute("newQuestionForm", nqf);

      return "index";
    }

    String image = null;

    if (!newQuestionForm.getImage().isEmpty()) {
      try {
        byte[] imageBin = newQuestionForm.getImage().getBytes();
        byte[] encoded = Base64.getEncoder().encode(imageBin);

        image = new String(encoded);
      } catch (Throwable e) {}
    }

    q.setImage(image);
    q.setValue(newQuestionForm.getValue());
    questionRepository.save(q);

    List<Answer> ans = q.getAnswers();

    try { Answer answerA = ans.get(0); answerA.setValue(newQuestionForm.getAnswerA()); answerA.setCorrect(newQuestionForm.getCheckboxA()); answerRepository.save(answerA); } catch (Throwable e) {}
    try { Answer answerB = ans.get(1); answerB.setValue(newQuestionForm.getAnswerB()); answerB.setCorrect(newQuestionForm.getCheckboxB()); answerRepository.save(answerB); } catch (Throwable e) {}
    try { Answer answerC = ans.get(2); answerC.setValue(newQuestionForm.getAnswerC()); answerC.setCorrect(newQuestionForm.getCheckboxC()); answerRepository.save(answerC); } catch (Throwable e) {}
    try { Answer answerD = ans.get(3); answerD.setValue(newQuestionForm.getAnswerD()); answerD.setCorrect(newQuestionForm.getCheckboxD()); answerRepository.save(answerD); } catch (Throwable e) {}

    return "redirect:/templates/" + templateId;
  }



  @PostMapping("/templates/delete/{templateId}/{questionId}")
  public String deleteQuestion (@PathVariable Long templateId, @PathVariable Long questionId) {
    User user = userRepository.findOneByEmail(securityService.getEmail()).orElse(null);
    if (user == null) {
      // throw 404
      return "redirect:/";
    }

    TestTemplate tt = testTemplateRepository.findById(templateId).orElse(null);
    if (tt == null) {
      // throw 404
      return "redirect:/templates/" + templateId;
    }

    if (!tt.getUser().equals(user)) {
      // throw 403
      return "redirect:/templates/" + templateId;
    }

    Question q = questionRepository.findById(questionId).orElse(null);
    if (q == null) {
      // throw 404
      return "redirect:/templates/" + templateId;
    }

    if (!q.getTestTemplate().equals(tt)) {
      // throw 403
      return "redirect:/templates/" + templateId;
    }

    questionRepository.deleteById(questionId);

    return "redirect:/templates/" + templateId;
  }
}
