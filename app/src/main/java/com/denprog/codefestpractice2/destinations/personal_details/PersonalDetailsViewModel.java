package com.denprog.codefestpractice2.destinations.personal_details;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denprog.codefestpractice2.room.AppDatabase;
import com.denprog.codefestpractice2.room.dao.AppDao;
import com.denprog.codefestpractice2.validator.Validator;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PersonalDetailsViewModel extends ViewModel {
    AppDao appDao;

    @Inject
    public PersonalDetailsViewModel(AppDatabase appDatabase) {
        this.appDao = appDatabase.getAppDao();
    }

    public MutableLiveData<PersonalDetailsFormState> personalDetailsFormStateMutableLiveData = new MutableLiveData<>(new PersonalDetailsFormState());
    public void onDataChange (String firstName, String middleName, String lastName, String cakeMessage) {
        PersonalDetailsFormState personalDetailsFormState = personalDetailsFormStateMutableLiveData.getValue();
        String firstNameError = Validator.validateNameInputs(firstName);
        String lastNameError = Validator.validateNameInputs(lastName);
        String middleNameError = Validator.validateNameInputs(middleName);
        String cakeError = Validator.validateNameInputs(cakeMessage);
        personalDetailsFormStateMutableLiveData.setValue(new PersonalDetailsFormState(firstNameError, middleNameError, lastNameError, cakeError));
    }

}