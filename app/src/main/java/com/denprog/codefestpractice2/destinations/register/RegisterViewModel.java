package com.denprog.codefestpractice2.destinations.register;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denprog.codefestpractice2.destinations.state.RegisterFormState;
import com.denprog.codefestpractice2.room.AppDatabase;
import com.denprog.codefestpractice2.room.dao.AppDao;
import com.denprog.codefestpractice2.room.entity.User;
import com.denprog.codefestpractice2.util.FileUtil;
import com.denprog.codefestpractice2.util.SimpleOperationCallback;
import com.denprog.codefestpractice2.validator.Validator;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RegisterViewModel extends ViewModel {
    AppDao appDao;
    MutableLiveData<Bitmap> selectedProfileUri = new MutableLiveData<>(null);
    @Inject
    public RegisterViewModel(AppDatabase appDatabase) {
        this.appDao = appDatabase.getAppDao();
    }

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

    public void register(Context context, Bitmap bitmap, String email, String username, String password, SimpleOperationCallback<User> simpleOperationCallback) {

        String path;
        if (bitmap != null) {
            path = FileUtil.saveUriToInternalStorageAndReturnPath(
                    context,
                    bitmap,
                    FileUtil.PROFILE_PICTURE_FOLDER_NAME,
                    username + FileUtil.generateRandomKeys(4),
                    username + FileUtil.PROFILE_PIC_EXTENSION);
        } else {
            simpleOperationCallback.onError("No Selected Profile Picture");
            return;
        }

        if (path == null) {
            simpleOperationCallback.onError("Something went wrong in loading the image.");
            return;
        }

        User user = new User(username, email, password, path);
        simpleOperationCallback.onLoading();
        CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(new Supplier<Long>() {
            @Override
            public Long get() {
                return appDao.insertUser(user);
            }
        });
        completableFuture.thenAcceptAsync(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) {
                List<User> users = appDao.getUserById(aLong);
                if (!users.isEmpty()) {
                    simpleOperationCallback.onFinished(users.get(0));
                } else {
                    simpleOperationCallback.onError("Something went wrong.");
                }
            }
        });
        completableFuture.exceptionally(new Function<Throwable, Long>() {
            @Override
            public Long apply(Throwable throwable) {
                simpleOperationCallback.onError(throwable.getLocalizedMessage());
                return 0L;
            }
        });
    }

}