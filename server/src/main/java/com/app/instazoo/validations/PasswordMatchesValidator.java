package com.app.instazoo.validations;

import com.app.instazoo.annotations.PasswordMatches;
import com.app.instazoo.payload.request.SignupRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        SignupRequest user = (SignupRequest) obj;
        return user.getPassword().equals(user.getConfirmPassword());
    }

}
