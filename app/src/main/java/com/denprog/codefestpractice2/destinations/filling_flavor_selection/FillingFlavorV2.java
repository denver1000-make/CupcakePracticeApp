package com.denprog.codefestpractice2.destinations.filling_flavor_selection;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class FillingFlavorV2 implements Parcelable {
    public String fillingFlavorName;
    public float fillingFlavorPrice;
    public FillingFlavorV2(String fillingFlavorName, float fillingFlavorPrice) {
        this.fillingFlavorName = fillingFlavorName;
        this.fillingFlavorPrice = fillingFlavorPrice;
    }

    protected FillingFlavorV2(Parcel in) {
        fillingFlavorName = in.readString();
        fillingFlavorPrice = in.readFloat();
    }

    public static final Creator<FillingFlavorV2> CREATOR = new Creator<FillingFlavorV2>() {
        @Override
        public FillingFlavorV2 createFromParcel(Parcel in) {
            return new FillingFlavorV2(in);
        }

        @Override
        public FillingFlavorV2[] newArray(int size) {
            return new FillingFlavorV2[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(fillingFlavorName);
        parcel.writeFloat(fillingFlavorPrice);
    }
}
