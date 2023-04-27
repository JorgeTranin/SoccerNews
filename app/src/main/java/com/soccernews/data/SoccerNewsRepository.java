package com.soccernews.data;

import androidx.room.Room;

import com.soccernews.App;
import com.soccernews.data.local.AppDatabase;
import com.soccernews.data.remote.NewsApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SoccerNewsRepository {

    //region Constantes
    private static final String REMOTE_API_URL = "https://jorgetranin.github.io/soccer-news-api/";
    private static final String LOCAL_DB_NAME = "soccer-news";
    //endregion

    //region Atributos: encapsulam o acesso a nossa API (Retrofit) e banco de dados local (Room).
    private NewsApi remoteApi;
    private AppDatabase localDb;

    public NewsApi getRemoteApi() {
        return remoteApi;
    }

    public AppDatabase getLocalDb() {
        return localDb;
    }
    //endregion

    //region Singleton: garante uma instância única dos atributos relacionados ao Retrofit e Room.
    private SoccerNewsRepository() {
        remoteApi = new Retrofit.Builder()
                .baseUrl(REMOTE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsApi.class);

        localDb = Room.databaseBuilder(App.getInstance(), AppDatabase.class, LOCAL_DB_NAME).build();
    }

    public static SoccerNewsRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final SoccerNewsRepository INSTANCE = new SoccerNewsRepository();
    }
    //endregion
}
