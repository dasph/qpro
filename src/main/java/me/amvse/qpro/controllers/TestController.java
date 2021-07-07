package me.amvse.qpro.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import me.amvse.qpro.forms.JoinTestForm;
import me.amvse.qpro.forms.NewTestForm;
import me.amvse.qpro.forms.SubmitTestForm;
import me.amvse.qpro.models.Answer;
import me.amvse.qpro.models.AnswerSubmission;
import me.amvse.qpro.models.Question;
import me.amvse.qpro.models.Test;
import me.amvse.qpro.models.TestTemplate;
import me.amvse.qpro.models.User;
import me.amvse.qpro.repositories.AnswerRepository;
import me.amvse.qpro.repositories.AnswerSubmissionRepository;
import me.amvse.qpro.repositories.QuestionRepository;
import me.amvse.qpro.repositories.TestRepository;
import me.amvse.qpro.repositories.TestTemplateRepository;
import me.amvse.qpro.repositories.UserRepository;
import me.amvse.qpro.service.SecurityService;

@Controller
@Transactional
public class TestController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TestTemplateRepository testTemplateRepository;

  @Autowired
  private TestRepository testRepository;

  @Autowired
  private QuestionRepository questionRepository;

  @Autowired
  private AnswerRepository answerRepository;

  @Autowired
  private AnswerSubmissionRepository answerSubmissionRepository;

  @Autowired
  private SecurityService securityService;

  @GetMapping("/tests")
  public String getAllTests (Model model) {
    User user = userRepository.findOneByEmail(securityService.getEmail()).orElse(null);
    if (user == null) {
      // throw 404
      return "index";
    }

    List<Test> tests = user.getTests();

    model.addAttribute("view", "tests");
    model.addAttribute("tests", tests);

    return "index";
  }

  @PostMapping("/tests")
  public String createTest (@ModelAttribute("newTestForm") NewTestForm newTestForm, BindingResult bindingResult, Model model) {
    User user = userRepository.findOneByEmail(securityService.getEmail()).orElse(null);
    if (user == null) {
      // throw 404
      return "redirect:/";
    }

    TestTemplate tt = testTemplateRepository.findById(newTestForm.getTemplateId()).orElse(null);
    if (tt == null) {
      // throw 404
      return "redirect:/templates";
    }

    if (!tt.getUser().equals(user)) {
      // throw 403
      return "redirect:/templates";
    }

    Test test = new Test(getRandomKey(), true);

    user.addTest(test);
    tt.addTest(test);

    testRepository.save(test);

    return "redirect:/tests";
  }

  private static char[] b64Table = "-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".toCharArray();

  private static String getRandomKey () {
    String uid = "";
    byte[] array = new byte[16];

    new Random().nextBytes(array);
    for (int i = 0; i < 16; i++) uid += b64Table[(array[i] & 0xFF) % 64];

    return uid;
  }

  @PostMapping("/tests/delete/{testId}")
  public String deleteTest (@PathVariable Long testId) {
    User user = userRepository.findOneByEmail(securityService.getEmail()).orElse(null);
    if (user == null) {
      // throw 404
      return "redirect:/";
    }

    Test test = testRepository.findById(testId).orElse(null);
    if (test == null) {
      // throw 404
      return "redirect:/tests";
    }

    if (!test.getUser().equals(user)) {
      // throw 403
      return "redirect:/tests";
    }

    testRepository.deleteById(testId);

    return "redirect:/tests";
  }

  @PostMapping("/tests/toggle/{testId}")
  public String toggleTest (@PathVariable Long testId) {
    User user = userRepository.findOneByEmail(securityService.getEmail()).orElse(null);
    if (user == null) {
      // throw 404
      return "redirect:/";
    }

    Test test = testRepository.findById(testId).orElse(null);
    if (test == null) {
      // throw 404
      return "redirect:/tests";
    }

    if (!test.getUser().equals(user)) {
      // throw 403
      return "redirect:/tests";
    }

    test.setActive(!test.getActive());
    testRepository.save(test);

    return "redirect:/tests";
  }

  @PostMapping("/tests/join")
  public String joinTest (@ModelAttribute("joinTestForm") JoinTestForm joinTestForm, BindingResult bindingResult, Model model) {
    User user = userRepository.findOneByEmail(securityService.getEmail()).orElse(null);
    if (user == null) {
      // throw 404
      return "redirect:/";
    }

    Test test = testRepository.findOneByKey(joinTestForm.getKey()).orElse(null);
    if (test == null) {
      // TODO: Present to the user a 404 error
      return "redirect:/";
    }

    if (!test.getActive()) {
      // TODO: Show test disabled error
      // return "index";
      return "redirect:/";
    }

    if (!answerSubmissionRepository.findByUserIdAndTestId(user.getId(), test.getId()).isEmpty()) {
      // TODO: Redirect to show test results
      return "redirect:/tests/" + test.getId() + "/" + user.getId();
    }

    model.addAttribute("view", "showTest");
    model.addAttribute("test", test);

    return "index";
  }

  @PostMapping("/tests/{testId}")
  public String addTestSubmission (@ModelAttribute("submitTestForm") SubmitTestForm submitTestForm, BindingResult bindingResult, @PathVariable Long testId, Model model) {
    User user = userRepository.findOneByEmail(securityService.getEmail()).orElse(null);
    if (user == null) {
      // throw 404
      return "redirect:/";
    }
    Test test = testRepository.findById(testId).orElse(null);
    if (test == null) {
      // TODO: Present to the user a 404 error
      return "redirect:/";
    }
    if (!test.getActive()) {
      // TODO: Show test disabled error
      // return "index";
      return "redirect:/";
    }

    if (!answerSubmissionRepository.findByUserIdAndTestId(user.getId(), test.getId()).isEmpty()) {
      // TODO: Redirect to show test results
      return "redirect:/tests";
    }

    submitTestForm.getAnswers().forEach((answerId, questionId) -> {
      AnswerSubmission as = new AnswerSubmission();

      user.addAnswerSubmission(as);
      test.addAnswerSubmission(as);

      // TODO: handle null Q/A
      Question question = questionRepository.findById(questionId).orElse(null);
      Answer answer = answerRepository.findById(answerId).orElse(null);

      question.addAnswerSubmission(as);
      answer.addAnswerSubmission(as);

      answerSubmissionRepository.save(as);
    });

    return "redirect:/tests/" + test.getId() + "/" + user.getId();
  }

  @GetMapping("/tests/{testId}/{userId}")
  public String showTestResults (@PathVariable Long testId, @PathVariable Long userId, Model model) {
    User user = userRepository.findOneByEmail(securityService.getEmail()).orElse(null);
    if (user == null) {
      // throw 404
      return "redirect:/";
    }

    Test test = testRepository.findById(testId).orElse(null);
    if (test == null) {
      // throw 404
      return "redirect:/tests";
    }

    Map<Long, Long> submission = answerSubmissionRepository.findByUserIdAndTestId(userId, testId).stream().collect(Collectors.toMap(item -> item.getAnswer().getId(), item -> item.getQuestion().getId()));

    model.addAttribute("view", "showTestResults");
    model.addAttribute("test", test);
    model.addAttribute("submission", submission);
    return "index";
  }

  @GetMapping("/tests/{testId}")
  public String getSubmissions (@PathVariable Long testId, Model model) {
    User user = userRepository.findOneByEmail(securityService.getEmail()).orElse(null);
    if (user == null) {
      // throw 404
      return "index";
    }

    List<AnswerSubmission> as = answerSubmissionRepository.findByTestId(testId);

    List<User> users = new ArrayList<>();

    as.forEach((sub) -> {
      User u = sub.getUser();
      if (!users.contains(u)) users.add(u);
    });

    model.addAttribute("view", "submissions");
    model.addAttribute("testId", testId);
    model.addAttribute("users", users);

    return "index";
  }

  @PostMapping("/tests/delete/{testId}/{userId}")
  public String deleteSubmission (@PathVariable Long testId, @PathVariable Long userId) {
    User user = userRepository.findOneByEmail(securityService.getEmail()).orElse(null);
    if (user == null) {
      // throw 404
      return "redirect:/";
    }

    Test test = testRepository.findById(testId).orElse(null);
    if (test == null) {
      // throw 404
      return "redirect:/tests";
    }

    answerSubmissionRepository.deleteByUserIdAndTestId(userId, testId);

    return "redirect:/tests/" + testId;
  }

}
