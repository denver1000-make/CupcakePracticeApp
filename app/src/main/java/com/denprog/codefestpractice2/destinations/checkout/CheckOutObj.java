package com.denprog.codefestpractice2.destinations.checkout;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import javax.annotation.Nullable;

@Entity
public class CheckOutObj {
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
}
