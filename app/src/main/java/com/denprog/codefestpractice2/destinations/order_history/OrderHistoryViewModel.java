package com.denprog.codefestpractice2.destinations.order_history;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.denprog.codefestpractice2.destinations.checkout.CheckOutObj;
import com.denprog.codefestpractice2.room.AppDatabase;
import com.denprog.codefestpractice2.room.dao.AppDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class OrderHistoryViewModel extends ViewModel {
    MutableLiveData<List<CheckOutObj>> mutableLiveData = new MutableLiveData<List<CheckOutObj>>(new ArrayList<>());
    AppDao appDao;

    @Inject
    public OrderHistoryViewModel(AppDatabase appDatabase) {
        this.appDao = appDatabase.getAppDao();
    }

    public void fetchOrderHistory(long userId) {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                List<CheckOutObj> list = appDao.getAllCheckOutObj(userId);
                mutableLiveData.postValue(list);
            }
        });
    }


}
