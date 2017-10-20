package com.tsm.rf.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation to validade the Date. <br>
 * There is 3 types of validation. 'Today' is the mark to comparisons: <br>
 * FUTURE: Validates if the date is after of today. <br>
 * PAST: Validates if the date is the past of today. <br>
 * TODAY: Validates if the date is today.
 */
@Target({ FIELD, ANNOTATION_TYPE, PARAMETER, TYPE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = CustomDateValidator.class)
public @interface CustomDate {

    /** Custom date types. */
    public enum Type {
        FUTURE, PAST, FUTURE_TODAY, PAST_TODAY
    }

    String message() default "{correct.packageName.to.add}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Type type();

    /**
     * This allows the class level validations constraint to have multiple constraints, therefore a class can have several cross field
     * validations.
     */
    @Target({ TYPE, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        CustomDate[] value();
    }
}
