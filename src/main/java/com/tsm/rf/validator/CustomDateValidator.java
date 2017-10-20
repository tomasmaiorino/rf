package com.tsm.rf.validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

/**
 * This {@link CustomDateValidator} implements the validation logic for the {@link CustomDate} annotation.
 */
public class CustomDateValidator implements ConstraintValidator<CustomDate, Object> {

    private CustomDate.Type type;

    private final LocalDateTime today = LocalDate.now().atTime(0, 0, 0);

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void initialize(final CustomDate constraintAnnotation) {
        type = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            if (Objects.isNull(value)) {
                return true;
            }

            String date = (String) value;
            if (StringUtils.isEmpty(date)) {
                return false;
            }

            LocalDateTime dateToValidate = LocalDate.parse(date, formatter).atTime(0, 0, 0);

            switch (type) {
                case FUTURE:
                    return today.isBefore(dateToValidate);
                case FUTURE_TODAY:
                    return today.isBefore(dateToValidate) || today.isEqual(dateToValidate);
                case PAST_TODAY:
                    return today.isAfter(dateToValidate) || today.isEqual(dateToValidate);
                case PAST:
                    return today.isAfter(dateToValidate);
                default:
                    return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
