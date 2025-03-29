package com.denprog.codefestpractice2.destinations.personal_details;

public class PersonalDetailsFormState {

    public String firstNameError = null;
    public String middleNameError = null;
    public String lastNameError = null;
    public String cakeMessageError = null;
    public Boolean isDataValid = false;
    public PersonalDetailsFormState(String firstNameError, String middleNameError, String lastNameError, String cakeMessageError) {
        this.firstNameError = firstNameError;
        this.middleNameError = middleNameError;
        this.lastNameError = lastNameError;
        this.cakeMessageError = cakeMessageError;
        if (this.firstNameError == null && this.middleNameError == null && lastNameError == null && cakeMessageError == null) {
            this.isDataValid = true;
        } else {
            this.isDataValid = false;
        }
    }

    public PersonalDetailsFormState(Boolean isDataValid) {
        this.isDataValid = isDataValid;
    }

    public PersonalDetailsFormState() {
        this.firstNameError = null;
        this.middleNameError = null;
        this.lastNameError = null;
        this.cakeMessageError = null;
        this.isDataValid = null;
    }
}
