package com.denprog.codefestpractice2.room.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = User.class,
                childColumns = "userId",
                parentColumns = "userId")
})
public class SavedUser {
    @PrimaryKey
    public long saveId = 1;
    public long userId;

    public SavedUser(long userId) {
        this.userId = userId;
    }
}
