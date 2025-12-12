package com.saebom.bulletinboard.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String> {

    private static final String REGEX = "^[가-힣a-zA-Z ]+$";

    public boolean isValid(String value, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        if (value == null || value.isBlank()) {
            addMessage(context, "이름을 입력해주세요.");
            return false;
        }

        int len = value.length();
        if (len < MemberValidationRules.NAME_MIN ||
            len > MemberValidationRules.NAME_MAX
        ) {
            String message = "이름은 " +
                    MemberValidationRules.NAME_MIN + " ~ " +
                    MemberValidationRules.NAME_MAX + "자 사이여야 합니다.";

            addMessage(context, message);
            return false;
        }

        if (!value.matches(REGEX)) {
            addMessage(context, "이름은 영문 대·소문자와 한글만 사용할수 있습니다.");
            return false;
        }

        return true;

    }

    private void addMessage(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}