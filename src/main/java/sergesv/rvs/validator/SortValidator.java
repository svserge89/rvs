package sergesv.rvs.validator;

import org.springframework.data.domain.Sort;
import sergesv.rvs.validator.annotation.SortConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static sergesv.rvs.util.ValidationUtil.checkSort;

public class SortValidator implements ConstraintValidator<SortConstraint, Sort> {
    private String[] allowedParams;

    @Override
    public void initialize(SortConstraint constraintAnnotation) {
        allowedParams = constraintAnnotation.allowedParams();
    }

    @Override
    public boolean isValid(Sort value, ConstraintValidatorContext context) {
        try {
            checkSort(value, allowedParams);
        } catch (Exception exception) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    exception.getMessage()).addConstraintViolation();

            return false;
        }

        return true;
    }
}
