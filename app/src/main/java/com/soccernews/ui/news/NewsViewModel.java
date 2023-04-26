package com.soccernews.ui.news;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.soccernews.data.remote.NewsApi;
import com.soccernews.domain.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsViewModel extends ViewModel {

    public enum State {
        DOING, DONE, ERRO
    }

    private final MutableLiveData<List<News>> mNews = new MutableLiveData<>();
    private final MutableLiveData<State> state = new MutableLiveData<>();

    private final NewsApi newsApi;


    public NewsViewModel() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jorgetranin.github.io/soccer-news-api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        newsApi = retrofit.create(NewsApi.class);
        this.findNews();


    }

    public void findNews() {
        state.setValue(State.DOING);
        newsApi.getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (response.isSuccessful()) {
                    mNews.setValue(response.body());
                    state.setValue(State.DONE);
                }
                state.setValue(State.ERRO);
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                state.setValue(State.ERRO);

            }
        });
    }

    public MutableLiveData<List<News>> getNews() {

        return mNews;
    }

    public MutableLiveData<State> getState() {

        return this.state;
    }

}