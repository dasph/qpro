package me.amvse.qpro.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.function.IntPredicate;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
  private int min;
  private int max;
  private boolean number;
  private boolean lower;
  private boolean upper;
  private boolean special;
  private boolean white;

  @Override
  public void initialize (final ValidPassword constraintAnnotation) {
    min = constraintAnnotation.min();
    max = constraintAnnotation.max();
    number = constraintAnnotation.number();
    lower = constraintAnnotation.lower();
    upper = constraintAnnotation.upper();
    special = constraintAnnotation.special();
    white = constraintAnnotation.white();
  }

  @Override
  public boolean isValid (String password, ConstraintValidatorContext context) {
    String error = "";

    if (password == null) error = "Please provide a password.";
    else if (password.length() < min) error = "Password is too short.";
    else if (password.length() > max) error = "Password is too long.";
    else if (number && !containsNumber(password)) error = "Password must contain 1 or more digit characters.";
    else if (upper && !containsUpperCase(password)) error = "Password must contain 1 or more uppercase characters.";
    else if (lower && !containsLowerCase(password)) error = "Password must contain 1 or more lowercase characters.";
    else if (special && !containsSpecial(password)) error = "Password must contain 1 or more special characters.";
    else if (!white && containsWhitespace(password)) error = "Password can not contain whitespace characters.";

    if (error.isEmpty()) return true;

    context.buildConstraintViolationWithTemplate(error).addConstraintViolation().disableDefaultConstraintViolation();
    return false;
  }

  private boolean containsLowerCase (String value) {
    return anyMatch(value, i -> Character.isLetter(i) && Character.isLowerCase(i));
  }

  private boolean containsUpperCase (String value) {
    return anyMatch(value, i -> Character.isLetter(i) && Character.isUpperCase(i));
  }

  private boolean containsNumber (String value) {
    return anyMatch(value, Character::isDigit);
  }

  private boolean containsWhitespace (String value) {
    return anyMatch(value, Character::isWhitespace);
  }

  private boolean containsSpecial (String value) {
    return !allMatch(value, Character::isLetterOrDigit);
  }

  private boolean anyMatch (String value, IntPredicate predicate) {
    return value.chars().anyMatch(predicate);
  }

  private boolean allMatch (String value, IntPredicate predicate) {
    return value.chars().allMatch(predicate);
  }
}
