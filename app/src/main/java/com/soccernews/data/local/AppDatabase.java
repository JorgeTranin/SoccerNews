package com.soccernews.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.soccernews.domain.News;

@Database(entities = {News.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}
