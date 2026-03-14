package com.wl.big_things.validation;

import com.wl.big_things.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        if(s.equals("已发布")||s.equals("草稿"))
        {
            return true;
        }
        return false;
    }
}
