package me.amvse.qpro.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import me.amvse.qpro.forms.NewTemplateForm;
import me.amvse.qpro.models.TestTemplate;

@Component
public class NewTemplateFormValidator implements Validator {
  @Override
  public boolean supports (Class<?> aClass) {
    return TestTemplate.class.equals(aClass);
  }

  @Override
  public void validate (Object o, Errors errors) {
    NewTemplateForm form = (NewTemplateForm)o;

    String name = form.getName().trim();

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
    if (name.length() < 2 || name.length() > 128) errors.rejectValue("name", "newTemplateForm.name.size");
  }
}
