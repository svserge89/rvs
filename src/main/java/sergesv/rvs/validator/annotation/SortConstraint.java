package sergesv.rvs.validator.annotation;

import sergesv.rvs.validator.SortValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SortValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SortConstraint {
    String message() default "Invalid sort parameters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String[] allowedParams();
}
