package com.denprog.codefestpractice2.destinations.cake_flavor_selection.obj;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CakeFlavorV2 implements Parcelable {

    public String cakeFlavorName;
    public float cakeFlavorPrice;

    public CakeFlavorV2(String cakeFlavorName, float cakeFlavorPrice) {
        this.cakeFlavorName = cakeFlavorName;
        this.cakeFlavorPrice = cakeFlavorPrice;
    }

    protected CakeFlavorV2(Parcel in) {
        cakeFlavorName = in.readString();
        cakeFlavorPrice = in.readFloat();
    }

    public static final Creator<CakeFlavorV2> CREATOR = new Creator<CakeFlavorV2>() {
        @Override
        public CakeFlavorV2 createFromParcel(Parcel in) {
            return new CakeFlavorV2(in);
        }

        @Override
        public CakeFlavorV2[] newArray(int size) {
            return new CakeFlavorV2[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(cakeFlavorName);
        parcel.writeFloat(cakeFlavorPrice);
    }
}
