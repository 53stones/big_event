package com.wl.big_things.anno;

import com.wl.big_things.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {StateValidation.class}
)
public @interface State {
    String message() default "state参数的值只能是草稿或已发布";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
