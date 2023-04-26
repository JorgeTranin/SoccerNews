package com.soccernews.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.soccernews.domain.News;

import java.util.List;

@Dao
public interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(News... news);

    @Query("SELECT * FROM News WHERE favorite = 1")
    List<News> loadFavoriteNews();

    @Query("SELECT * FROM news")
    List<News> getAll();


}
