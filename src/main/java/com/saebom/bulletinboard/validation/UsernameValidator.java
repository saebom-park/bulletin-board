package com.saebom.bulletinboard.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, String> {

    private static final String REGEX = "^[a-zA-Z0-9]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        if (value == null || value.isBlank()) {
            addMessage(context, "아이디를 입력해 주세요.");
            return false;
        }

        int len = value.length();

        if (len < MemberValidationRules.USERNAME_MIN ||
            len > MemberValidationRules.USERNAME_MAX
        ) {
            String message = "아이디는 " +
                    MemberValidationRules.USERNAME_MIN + " ~ " +
                    MemberValidationRules.USERNAME_MAX + "자 사이여야 합니다.";

            addMessage(context, message);
            return false;
        }

        if (!value.matches(REGEX)) {
            addMessage(context, "아이디는 영문과 숫자만 사용할 수 있습니다.");
            return false;
        }

        return true;

    }

    private void addMessage(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}