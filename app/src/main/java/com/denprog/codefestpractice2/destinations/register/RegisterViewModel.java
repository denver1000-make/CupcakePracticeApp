package com.denprog.codefestpractice2.destinations.register;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denprog.codefestpractice2.destinations.state.RegisterFormState;
import com.denprog.codefestpractice2.validator.Validator;

public class RegisterViewModel extends ViewModel {
    public MutableLiveData<RegisterFormState> mutableLiveData = new MediatorLiveData<>(null);

    public void onDataChanged(String email, String username, String password, String confirmPassword) {
        RegisterFormState registerFormState = new RegisterFormState(null, null, null, null,false);
        String userNameError = Validator.validateNameInputs(username);
        String emailError = Validator.validateEmailInputs(email);
        String passwordError = Validator.validatePassword(password);
        String confirmPasswordError = Validator.validateConfirmPassword(password, confirmPassword);
        if (userNameError != null) {
            registerFormState.userNameError = userNameError;
            registerFormState.isDataValid = false;
            this.mutableLiveData.setValue(registerFormState);
        } else if (emailError != null) {
            registerFormState.emailError = emailError;
            registerFormState.isDataValid = false;
            this.mutableLiveData.setValue(registerFormState);
        } else if (passwordError != null) {
            registerFormState.passwordError = passwordError;
            registerFormState.isDataValid = false;
            this.mutableLiveData.setValue(registerFormState);
        } else if (confirmPasswordError != null) {
            registerFormState.confirmPasswordError = confirmPasswordError;
            registerFormState.isDataValid = false;
            this.mutableLiveData.setValue(registerFormState);
        } else {
            registerFormState.isDataValid = true;
            this.mutableLiveData.setValue(registerFormState);
        }
    }

    public void onDataChangedV2(String email, String username, String password, String confirmPassword) {
        RegisterFormState registerFormState = new RegisterFormState(null, null, null, null,false);
        String userNameError = Validator.validateNameInputs(username);
        String emailError = Validator.validateEmailInputs(email);
        String passwordError = Validator.validatePassword(password);
        String confirmPasswordError = Validator.validateConfirmPassword(password, confirmPassword);
        if (userNameError != null || emailError != null || passwordError != null || confirmPasswordError != null) {
            registerFormState.confirmPasswordError = confirmPasswordError;
            registerFormState.passwordError = passwordError;
            registerFormState.emailError = emailError;
            registerFormState.userNameError = userNameError;
            registerFormState.isDataValid = false;
        } else {
            registerFormState.isDataValid = true;
        }
        this.mutableLiveData.setValue(registerFormState);
    }

}