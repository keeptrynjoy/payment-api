package toy.paymentapi.support.validator.constrain;

import toy.paymentapi.support.validator.constrainvalidators.NotEmptyCollectionValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyCollectionValidator.class)
public @interface NotEmptyCollection {

    String message() default "목록이 존재하지 않습니다.";

    Class<?>[] groups() default {};
}
