package me.amvse.qpro.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import me.amvse.qpro.forms.NewQuestionForm;
import me.amvse.qpro.models.Question;


@Component
public class NewQuestionFormValidator implements Validator {
  @Override
  public boolean supports (Class<?> aClass) {
    return Question.class.equals(aClass);
  }

  @Override
  public void validate (Object o, Errors errors) {
    NewQuestionForm form = (NewQuestionForm)o;

    String value = form.getValue().trim();

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "NotEmpty");
    if (value.length() < 2 || value.length() > 512) errors.rejectValue("value", "newQuestionForm.value.size");
  }
}
