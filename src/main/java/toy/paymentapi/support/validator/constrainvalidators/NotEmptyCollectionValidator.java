package toy.paymentapi.support.validator.constrainvalidators;

import toy.paymentapi.support.validator.constrain.NotEmptyCollection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;
import java.util.Objects;

public class NotEmptyCollectionValidator implements ConstraintValidator<NotEmptyCollection, Collection<Long>> {

    @Override
    public boolean isValid(Collection<Long> value, ConstraintValidatorContext context) {

        if(value.isEmpty()){
            return false;
        }

        return value.stream().allMatch(Objects::nonNull);
    }

}
