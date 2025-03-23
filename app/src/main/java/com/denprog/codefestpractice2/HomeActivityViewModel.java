package com.denprog.codefestpractice2;

import androidx.lifecycle.ViewModel;

import com.denprog.codefestpractice2.room.AppDatabase;
import com.denprog.codefestpractice2.room.dao.AppDao;
import com.denprog.codefestpractice2.room.entity.User;
import com.denprog.codefestpractice2.util.SimpleOperationCallback;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeActivityViewModel extends ViewModel {

    AppDao appDao;

    @Inject
    public HomeActivityViewModel(AppDatabase appDatabase) {
        this.appDao = appDatabase.getAppDao();
    }

    public void loadUserInfoToHeader(String email, SimpleOperationCallback<User> userSimpleOperationCallback) {
        userSimpleOperationCallback.onLoading();
        CompletableFuture<User> completableFuture = CompletableFuture.supplyAsync(new Supplier<User>() {
            @Override
            public User get() {
                List<User> users = appDao.getUserByEmail(email);
                if (users.isEmpty()) {
                    throw new RuntimeException("User not found");
                }
                return users.get(0);
            }
        });

        completableFuture.thenAcceptAsync(new Consumer<User>() {
            @Override
            public void accept(User user) {
                userSimpleOperationCallback.onFinished(user);
            }
        });

        completableFuture.exceptionally(new Function<Throwable, User>() {
            @Override
            public User apply(Throwable throwable) {
                userSimpleOperationCallback.onError(throwable.getLocalizedMessage());
                return null;
            }
        });
    }
}
