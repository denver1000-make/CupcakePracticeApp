package com.denprog.codefestpractice2.destinations.checkout;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denprog.codefestpractice2.room.AppDatabase;
import com.denprog.codefestpractice2.room.dao.AppDao;
import com.denprog.codefestpractice2.util.FileUtil;
import com.denprog.codefestpractice2.util.MainThreadRunner;
import com.denprog.codefestpractice2.util.SimpleLambdaCallback;
import com.denprog.codefestpractice2.util.SimpleOperationCallback;
import com.denprog.codefestpractice2.util.TaskLoaderWithProgress;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;


@HiltViewModel
public class CheckOutViewModel extends ViewModel {
    public MutableLiveData<CheckOutState> mutableLiveData = new MutableLiveData<>(null);
    AppDao appDao;

    @Inject
    public CheckOutViewModel(AppDatabase appDatabase) {
        this.appDao = appDatabase.getAppDao();
    }

    public void checkOut(CheckOutObj checkOutObj) {
        mutableLiveData.postValue(new CheckOutState.Loading());
        CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(new Supplier<Long>() {
            @Override
            public Long get() {
                return appDao.insertCheckOut(checkOutObj);
            }
        });

        completableFuture.thenAcceptAsync(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) {
                mutableLiveData.postValue(new CheckOutState.Complete());
            }
        });

        completableFuture.exceptionally(new Function<Throwable, Long>() {
            @Override
            public Long apply(Throwable throwable) {
                mutableLiveData.postValue(new CheckOutState.Error(throwable.getMessage()));
                return null;
            }
        });
    }

    public void saveImageAndRetrievePath(Context context, Bitmap bitmap, String username, SimpleLambdaCallback<String> taskLoaderWithProgress) {
        mutableLiveData.postValue(new CheckOutState.Loading());
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                String path = FileUtil.saveUriToInternalStorageAndReturnPath(context, bitmap, CheckOutFragment.SAVED_DESIGN_PATHS, username, FileUtil.generateRandomKeys(4) + FileUtil.PROFILE_PIC_EXTENSION);
                MainThreadRunner.runOnMain(() -> taskLoaderWithProgress.doThing(path));
            } catch (Exception e) {
                MainThreadRunner.runOnMain(() -> taskLoaderWithProgress.doThing(null));
            }
        });
    }
}