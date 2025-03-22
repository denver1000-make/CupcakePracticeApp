package com.denprog.codefestpractice2.validator;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final Pattern spaceMatcher = Pattern.compile("\\s");
    private static final Pattern ILLEGAL_CHARACTERS_MATCHER = Pattern.compile("[*&!@#$%^()]");
    private static final int NAME_LENGTH = 5;
    private static final int PASSWORD_LENGTH = 8;

    public static boolean isInputEmpty(String input){
        return input == null || input.isEmpty() || input.isBlank();
    }

    public static boolean isEmailValid(String email) {
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(email);
        return matcher.matches();
    }

    public static boolean doesStringContainSpace(String input) {
        Matcher matcher = spaceMatcher.matcher(input);
        return matcher.find();
    }

    public static boolean isInputLengthInvalid(String input, int length) {
        return input.length() < length;
    }

    public static boolean hasIllegalShitInside(String input) {
        Matcher matcher = ILLEGAL_CHARACTERS_MATCHER.matcher(input);
        return matcher.matches();
    }

    public static String validateEmailInputs(String email) {
        if (isInputEmpty(email)) {
            return "Input empty";
        } else if (!isEmailValid(email)) {
            return "Email format is not valid";
        } else {
            return null;
        }
    }

    public static String validateNameInputs(String input) {
        if (isInputEmpty(input)) {
            return "Input Empty";
        } else if (isInputLengthInvalid(input, NAME_LENGTH)) {
            return "This field should at least be " + NAME_LENGTH + " characters.";
        } else if (hasIllegalShitInside(input)) {
            return "Must not contain illegal characters";
        } else {
            return null;
        }
    }

    public static String validatePassword(String password) {
        if (isInputEmpty(password)) {
            return "Field is empty";
        } else if (isInputLengthInvalid(password, PASSWORD_LENGTH)) {
            return "Password length should at least be " + PASSWORD_LENGTH + " characters";
        } else {
            return null;
        }
    }

    public static String validateConfirmPassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return "Does not match password";
        } else {
            return null;
        }
    }

    public static String normalValidation(String input) {
        if (isInputEmpty(input)) {
            return "Field is empty";
        } else {
            return null;
        }
    }

}
