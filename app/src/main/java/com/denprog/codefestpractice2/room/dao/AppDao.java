package com.denprog.codefestpractice2.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.denprog.codefestpractice2.destinations.checkout.CheckOutObj;
import com.denprog.codefestpractice2.room.entity.SavedUser;
import com.denprog.codefestpractice2.room.entity.User;

import java.util.List;

@Dao
public interface AppDao {
    @Insert
    long insertUser(User user);
    @Query("SELECT * FROM User WHERE email =:email AND password=:password")
    List<User> getUserByEmailAndPassword(String email, String password);
    @Query("SELECT * FROM User WHERE userId = :aLong")
    List<User> getUserById(Long aLong);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveUserIdToQuickLogin(SavedUser savedUser);
    @Query("SELECT * FROM SavedUser")
    List<SavedUser> getAllSavedUser();


    @Query("SELECT * FROM User WHERE email =:email")
    List<User> getUserByEmail(String email);

    @Insert
    long insertCheckOut(CheckOutObj checkOutObj);

    @Query("SELECT * FROM CheckOutObj WHERE userId =:userId")
    List<CheckOutObj> getAllCheckOutObj(long userId);

    @Query("DELETE FROM SavedUser")
    void clearSavedLogin();
}
