package me.amvse.qpro.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import me.amvse.qpro.forms.NewTemplateForm;
import me.amvse.qpro.models.TestTemplate;
import me.amvse.qpro.models.User;
import me.amvse.qpro.repositories.TestTemplateRepository;
import me.amvse.qpro.repositories.UserRepository;
import me.amvse.qpro.service.SecurityService;
import me.amvse.qpro.validators.NewTemplateFormValidator;

@Controller
public class TestTemplateController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TestTemplateRepository testTemplateRepository;

  @Autowired
  private SecurityService securityService;

  @Autowired
  private NewTemplateFormValidator newTemplateFormValidator;

  @GetMapping("/templates")
  public String templates (Model model) {
    User user = userRepository.findOneByEmail(securityService.getEmail()).orElse(null);
    if (user == null) {
      // throw 404
      return "index";
    }

    List<TestTemplate> tt = user.getTestTemplates();

    model.addAttribute("view", "templates");
    model.addAttribute("templates", tt);

    return "index";
  }

  @GetMapping("/templates/new")
  public String addTemplate (Model model) {
    model.addAttribute("view", "addTemplate");
    model.addAttribute("newTemplateForm", new NewTemplateForm());

    return "index";
  }

  @PostMapping("/templates")
  public String createTemplate (@ModelAttribute("newTemplateForm") NewTemplateForm newTemplateForm, BindingResult bindingResult, Model model) {
    newTemplateFormValidator.validate(newTemplateForm, bindingResult);
    if (bindingResult.hasErrors()) {
      model.addAttribute("view", "addTemplate");
      return "index";
    }

    User user = userRepository.findOneByEmail(securityService.getEmail()).orElse(null);
    if (user == null) {
      // throw 404
      return "index";
    }

    TestTemplate tt = new TestTemplate(newTemplateForm.getName());
    user.addTestTemplate(tt);

    testTemplateRepository.save(tt);

    return "redirect:/templates/" + tt.getId();
  }

  @PostMapping("/templates/delete/{templateId}")
  public String deleteTemplate (@PathVariable Long templateId) {
    User user = userRepository.findOneByEmail(securityService.getEmail()).orElse(null);
    if (user == null) {
      // throw 404
      return "redirect:/templates";
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

    testTemplateRepository.deleteById(templateId);

    return "redirect:/templates";
  }
}
