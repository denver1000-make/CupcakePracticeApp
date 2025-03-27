package com.denprog.codefestpractice2.destinations.design;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DesigPickerFragmentViewModel extends ViewModel {
    public MutableLiveData<Bitmap> bitmapMutableLiveData = new MediatorLiveData<>();
}
