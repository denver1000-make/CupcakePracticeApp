package com.denprog.codefestpractice2.destinations.design;

import android.graphics.Bitmap;
import android.net.Uri;

import com.denprog.codefestpractice2.base.SelectionBase;

public class Design extends SelectionBase {
    public Bitmap bitmap;
    public Design(String name, float price, Bitmap bitmap) {
        super(name, price);
        this.bitmap = bitmap;
    }
}
