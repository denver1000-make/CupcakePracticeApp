package com.denprog.codefestpractice2.destinations.checkout;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import javax.annotation.Nullable;

@Entity
public class CheckOutObj implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int checkOutEntryId;
    public long userId;
    public String selectedCakeFlavor;
    public float cakeFlavorPrice;
    public String selectedWhippedFlavor;
    public float whippedFlavorPrice;
    @Nullable
    public String fillingFlavor = null;
    @ColumnInfo
    public float fillingFlavorPrice = -0f;
    public String designPath;
    public float designPrice;
    public String cakeMessage;
    public float totalPrice;

    @Ignore
    public CheckOutObj(String selectedCakeFlavor, float cakeFlavorPrice, String selectedWhippedFlavor, float whippedFlavorPrice, String fillingFlavor, float fillingFlavorPrice, String designPath, float designPrice) {
        this.selectedCakeFlavor = selectedCakeFlavor;
        this.cakeFlavorPrice = cakeFlavorPrice;
        this.selectedWhippedFlavor = selectedWhippedFlavor;
        this.whippedFlavorPrice = whippedFlavorPrice;
        this.fillingFlavor = fillingFlavor;
        this.fillingFlavorPrice = fillingFlavorPrice;
        this.designPath = designPath;
        this.designPrice = designPrice;
    }

    @Ignore
    public CheckOutObj(long userId, String selectedCakeFlavor, float cakeFlavorPrice, String selectedWhippedFlavor, float whippedFlavorPrice, @Nullable String fillingFlavor, float fillingFlavorPrice, String designPath, float designPrice) {
        this.userId = userId;
        this.selectedCakeFlavor = selectedCakeFlavor;
        this.cakeFlavorPrice = cakeFlavorPrice;
        this.selectedWhippedFlavor = selectedWhippedFlavor;
        this.whippedFlavorPrice = whippedFlavorPrice;
        this.fillingFlavor = fillingFlavor;
        this.fillingFlavorPrice = fillingFlavorPrice;
        this.designPath = designPath;
        this.designPrice = designPrice;
    }

    @Ignore
    public CheckOutObj(long userId, String selectedCakeFlavor, float cakeFlavorPrice, String selectedWhippedFlavor, float whippedFlavorPrice, @Nullable String fillingFlavor, float fillingFlavorPrice, String designPath, float designPrice, String cakeMessage) {
        this.userId = userId;
        this.selectedCakeFlavor = selectedCakeFlavor;
        this.cakeFlavorPrice = cakeFlavorPrice;
        this.selectedWhippedFlavor = selectedWhippedFlavor;
        this.whippedFlavorPrice = whippedFlavorPrice;
        this.fillingFlavor = fillingFlavor;
        this.fillingFlavorPrice = fillingFlavorPrice;
        this.designPath = designPath;
        this.designPrice = designPrice;
        this.cakeMessage = cakeMessage;
    }

    public CheckOutObj(long userId, String selectedCakeFlavor, float cakeFlavorPrice, String selectedWhippedFlavor, float whippedFlavorPrice, @Nullable String fillingFlavor, float fillingFlavorPrice, String designPath, float designPrice, String cakeMessage, float totalPrice) {
        this.userId = userId;
        this.selectedCakeFlavor = selectedCakeFlavor;
        this.cakeFlavorPrice = cakeFlavorPrice;
        this.selectedWhippedFlavor = selectedWhippedFlavor;
        this.whippedFlavorPrice = whippedFlavorPrice;
        this.fillingFlavor = fillingFlavor;
        this.fillingFlavorPrice = fillingFlavorPrice;
        this.designPath = designPath;
        this.designPrice = designPrice;
        this.cakeMessage = cakeMessage;
        this.totalPrice = totalPrice;
    }

    protected CheckOutObj(Parcel in) {
        checkOutEntryId = in.readInt();
        userId = in.readLong();
        selectedCakeFlavor = in.readString();
        cakeFlavorPrice = in.readFloat();
        selectedWhippedFlavor = in.readString();
        whippedFlavorPrice = in.readFloat();
        fillingFlavor = in.readString();
        fillingFlavorPrice = in.readFloat();
        designPath = in.readString();
        designPrice = in.readFloat();
        cakeMessage = in.readString();
        totalPrice = in.readFloat();
    }

    public static final Creator<CheckOutObj> CREATOR = new Creator<CheckOutObj>() {
        @Override
        public CheckOutObj createFromParcel(Parcel in) {
            return new CheckOutObj(in);
        }

        @Override
        public CheckOutObj[] newArray(int size) {
            return new CheckOutObj[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(checkOutEntryId);
        parcel.writeLong(userId);
        parcel.writeString(selectedCakeFlavor);
        parcel.writeFloat(cakeFlavorPrice);
        parcel.writeString(selectedWhippedFlavor);
        parcel.writeFloat(whippedFlavorPrice);
        parcel.writeString(fillingFlavor);
        parcel.writeFloat(fillingFlavorPrice);
        parcel.writeString(designPath);
        parcel.writeFloat(designPrice);
        parcel.writeString(cakeMessage);
        parcel.writeFloat(totalPrice);
    }
}
