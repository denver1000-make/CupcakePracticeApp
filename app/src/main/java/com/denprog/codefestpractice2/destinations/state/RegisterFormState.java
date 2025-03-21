package com.denprog.codefestpractice2.destinations.state;

public class RegisterFormState {

    public String userNameError;
    public String emailError;
    public String passwordError;
    public String confirmPasswordError;
    public boolean isDataValid;



    public RegisterFormState(String userNameError, String emailError, String passwordError, String confirmPasswordError, boolean isDataValid) {
        this.userNameError = userNameError;
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
        this.isDataValid = isDataValid;
    }
}