package com.saebom.bulletinboard.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static final String REGEX = "^[a-zA-Z0-9!@#$%^*+=-]+$";

    public boolean isValid(String value, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        if (value == null || value.isBlank()) {
            addMessage(context, "패스워드를 입력해주세요.");
            return false;
        }

        int len = value.length();
        if (len < MemberValidationRules.PASSWORD_MIN ||
            len > MemberValidationRules.PASSWORD_MAX
        ) {
            String message = "패스워드는 " +
                    MemberValidationRules.PASSWORD_MIN + " ~ " +
                    MemberValidationRules.PASSWORD_MAX + "자 사이여야 합니다.";

            addMessage(context, message);
            return false;
        }

        if (!value.matches(REGEX)) {
            addMessage(context, "패스워드는 영문 대·소문자, 숫자, 특수문자만 사용할 수 있습니다.");
            return false;
        }

        boolean hasLetter = value.chars().anyMatch(ch ->
                (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
        );

        boolean hasDigit = value.chars().anyMatch(ch ->
                (ch >= '0' && ch <= '9')
        );

        boolean hasSpecial = value.chars().anyMatch(ch ->
                "!@#$%^*+=-".indexOf(ch) >= 0
        );

        int typeCount = 0;
        if (hasLetter) typeCount++;
        if (hasDigit) typeCount++;
        if (hasSpecial) typeCount++;

        if (typeCount < 3) {
            addMessage(context, "패스워드는 영문, 숫자, 특수문자 모두를 포함해야 합니다.");
            return false;
        }

        return true;

    }

    private void addMessage(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}