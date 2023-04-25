package com.soccernews.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.soccernews.data.NewsApi;
import com.soccernews.databinding.FragmentNewsBinding;
import com.soccernews.domain.News;
import com.soccernews.ui.news.adapternews.NewsAdapter;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private NewsApi newsApi;
    private NewsAdapter newsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NewsViewModel newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //set de um layout para minha recycler view
        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));

        setupHttpClient();
        setupNewsList();
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            //passa para meu adapter a lista a ser consumida
            binding.rvNews.setAdapter(new NewsAdapter(news));
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupHttpClient() {
        //TODO Implementar a chamada retrofit

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jorgetranin.github.io/soccer-news-api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        newsApi = retrofit.create(NewsApi.class);

    }

    private void setupNewsList() {
        //TODO: listar as partidas consumindo nossa API REST
        binding.rvNews.setHasFixedSize(true);
        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        newsAdapter = new NewsAdapter(Collections.emptyList());
        binding.rvNews.setAdapter(newsAdapter);
        findNewsFromApi();

    }

    //--------------------------------------------------------------------Metodo para consumir a API do GitHubPages-------------------------------------------------------
    private void findNewsFromApi() {
        newsApi.getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (response.isSuccessful()) {
                    List<News> news = response.body();
                    newsAdapter = new NewsAdapter(news);
                    binding.rvNews.setAdapter(newsAdapter);
                } else {
                    showErroMessage();
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                showErroMessage();
            }
        });
    }

    private void showErroMessage() {
        Toast.makeText(getContext(), "Erro na api", Toast.LENGTH_LONG).show();
    }

}