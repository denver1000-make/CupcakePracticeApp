package com.denprog.codefestpractice2.room.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public long userId;
    public String username;
    public String email;
    public String password;
    public String profilePicPath;

    public User(String username, String email, String password, String profilePicPath) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePicPath = profilePicPath;
    }

    protected User(Parcel in) {
        userId = in.readLong();
        username = in.readString();
        email = in.readString();
        password = in.readString();
        profilePicPath = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(userId);
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(profilePicPath);
    }
}
