package com.denprog.codefestpractice2.destinations.login;

public class LoginFormState {

    public String emailFieldError = null;
    public String passwordFieldError = null;
    public Boolean isDataValid = null;

    public LoginFormState(String emailFieldError, String passwordFieldError, Boolean isDataValid) {
        this.emailFieldError = emailFieldError;
        this.passwordFieldError = passwordFieldError;
        this.isDataValid = isDataValid;
    }
}
