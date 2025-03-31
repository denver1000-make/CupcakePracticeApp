package com.denprog.codefestpractice2.destinations.order_history;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.denprog.codefestpractice2.destinations.checkout.CheckOutObj;
import com.denprog.codefestpractice2.room.AppDatabase;
import com.denprog.codefestpractice2.room.dao.AppDao;
import com.denprog.codefestpractice2.util.SimpleOperationCallback;

import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class OrderHistoryFragmentViewModel extends ViewModel {
    AppDao appDao;

    @Inject
    public OrderHistoryFragmentViewModel(AppDatabase appDatabase) {
        this.appDao = appDatabase.getAppDao();
    }

    public void fetchHistory(long userId, SimpleOperationCallback<List<CheckOutObj>> checkOutObj) {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                List<CheckOutObj> checkOutObjs = appDao.getAllCheckOutObj(userId);
                checkOutObj.onFinished(checkOutObjs);
            }
        });
    }
}
