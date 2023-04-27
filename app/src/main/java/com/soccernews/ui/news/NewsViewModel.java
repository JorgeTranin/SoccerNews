package com.soccernews.ui.news;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.soccernews.data.SoccerNewsRepository;
import com.soccernews.domain.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel {

    public enum State {
        DOING, DONE, ERRO
    }

    private final MutableLiveData<List<News>> mNews = new MutableLiveData<>();
    private final MutableLiveData<State> state = new MutableLiveData<>();

    public NewsViewModel() {

        this.findNews();


    }

    public void findNews() {
        state.setValue(State.DOING);
        SoccerNewsRepository.getInstance().getRemoteApi().getNews().enqueue(new Callback<List<News>>() {
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

    public void saveNews(News news) {
        AsyncTask.execute(() -> SoccerNewsRepository.getInstance().getLocalDb().newsDao().save(news));
    }

}