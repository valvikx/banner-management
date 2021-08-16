package by.valvik.bannermanagement.validation.annotation;

import by.valvik.bannermanagement.validation.impl.UniqueConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {UniqueConstraintValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface Unique {

    String message() default "{template.unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
