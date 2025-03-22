package com.denprog.codefestpractice2.hilt.module;

import android.content.Context;

import androidx.room.Room;

import com.denprog.codefestpractice2.room.AppDatabase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    public static AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "AppDatabase").fallbackToDestructiveMigration().build();
    }

}
