package com.denprog.codefestpractice2.destinations.login;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denprog.codefestpractice2.room.AppDatabase;
import com.denprog.codefestpractice2.room.dao.AppDao;
import com.denprog.codefestpractice2.room.entity.SavedUser;
import com.denprog.codefestpractice2.room.entity.User;
import com.denprog.codefestpractice2.util.SimpleLambdaCallback;
import com.denprog.codefestpractice2.util.SimpleOperationCallback;
import com.denprog.codefestpractice2.validator.Validator;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    AppDao appDao;

    public MutableLiveData<LoginFormState> loginFormStateMutableLiveData = new MediatorLiveData<>();

    @Inject
    public LoginViewModel(AppDatabase appDatabase) {
        this.appDao = appDatabase.getAppDao();
    }

    public void checkIfThereIsSavedLogin(SimpleLambdaCallback<User> simpleLambdaCallback) {
        CompletableFuture.supplyAsync(new Supplier<User>() {
            @Override
            public User get() {
                List<SavedUser> savedUsers = appDao.getAllSavedUser();
                if (savedUsers.isEmpty()) {
                    simpleLambdaCallback.doThing(null);
                } else {
                    long userId = savedUsers.get(0).userId;
                    List<User> users = appDao.getUserById(userId);
                    simpleLambdaCallback.doThing(users.isEmpty() ? null : users.get(0));
                }
                return null;
            }
        });
    }


    public CompletableFuture<User> login (String email, String password) {
        return CompletableFuture.supplyAsync(new Supplier<User>() {
            @Override
            public User get() {
                List<User> users = appDao.getUserByEmailAndPassword(email, password);
                if (users.isEmpty()) {
                    throw new RuntimeException("User not found.");
                }
                return users.get(0);
            }
        });
    }

    public void onDataChanged(String email, String password) {
        LoginFormState loginFormState = loginFormStateMutableLiveData.getValue() == null ? new LoginFormState(null, null, null) : loginFormStateMutableLiveData.getValue();
        loginFormState.emailFieldError = Validator.validateEmailInputs(email);
        loginFormState.passwordFieldError = Validator.normalValidation(password);

        if (loginFormState.emailFieldError == null && loginFormState.passwordFieldError == null) {
            loginFormState.isDataValid = true;
        } else {
            loginFormState.isDataValid = false;
        }
        loginFormStateMutableLiveData.setValue(loginFormState);
    }

    public void saveUserToDb(User user, SimpleOperationCallback<User> simpleOperationCallback) {
        simpleOperationCallback.onLoading();
        CompletableFuture.supplyAsync(new Supplier<Void>() {
            @Override
            public Void get() {
                appDao.saveUserIdToQuickLogin(new SavedUser(user.userId));
                return null;
            }
        }).thenAcceptAsync(new Consumer<Void>() {
            @Override
            public void accept(Void unused) {
                simpleOperationCallback.onFinished(user);
            }
        });
    }
}