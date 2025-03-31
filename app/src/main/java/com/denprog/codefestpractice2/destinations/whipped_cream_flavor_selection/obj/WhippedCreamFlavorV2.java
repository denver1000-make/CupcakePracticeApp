package com.denprog.codefestpractice2.destinations.whipped_cream_flavor_selection.obj;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class WhippedCreamFlavorV2 implements Parcelable {
    public String flavorName;
    public float flavorPrice;

    public WhippedCreamFlavorV2(String flavorName, float flavorPrice) {
        this.flavorName = flavorName;
        this.flavorPrice = flavorPrice;
    }

    protected WhippedCreamFlavorV2(Parcel in) {
        flavorName = in.readString();
        flavorPrice = in.readFloat();
    }

    public static final Creator<WhippedCreamFlavorV2> CREATOR = new Creator<WhippedCreamFlavorV2>() {
        @Override
        public WhippedCreamFlavorV2 createFromParcel(Parcel in) {
            return new WhippedCreamFlavorV2(in);
        }

        @Override
        public WhippedCreamFlavorV2[] newArray(int size) {
            return new WhippedCreamFlavorV2[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(flavorName);
        parcel.writeFloat(flavorPrice);
    }
}
