package me.amvse.qpro.constraints;

import javax.validation.Payload;
import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidPassword {
  String message () default "Invalid Password";
  Class <?> [] groups () default {};
  Class <? extends Payload> [] payload () default {};
  int min () default 8;
  int max () default 32;
  boolean number () default true;
  boolean lower () default true;
  boolean upper () default true;
  boolean special () default true;
  boolean white () default false;
}
