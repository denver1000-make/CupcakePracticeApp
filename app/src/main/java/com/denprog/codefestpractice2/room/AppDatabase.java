package com.denprog.codefestpractice2.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.denprog.codefestpractice2.destinations.checkout.CheckOutObj;
import com.denprog.codefestpractice2.room.dao.AppDao;
import com.denprog.codefestpractice2.room.entity.SavedUser;
import com.denprog.codefestpractice2.room.entity.User;

@Database(version = 7, entities = {User.class, SavedUser.class, CheckOutObj.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao getAppDao();
}
