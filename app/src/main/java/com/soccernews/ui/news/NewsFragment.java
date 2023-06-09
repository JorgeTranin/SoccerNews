package com.soccernews.ui.news;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.soccernews.databinding.FragmentNewsBinding;
import com.soccernews.ui.news.adapternews.NewsAdapter;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private NewsViewModel newsViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        observeNews();

        newsViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case DOING:
                    //TODO implementar o swipeRefresh modo carregando
                    binding.swipeRefreshLayout.setRefreshing(true);
                    break;
                case DONE:
                    //TODO parar o swipeRefresh modo carregando
                    binding.swipeRefreshLayout.setRefreshing(false);
                    break;
                case ERRO:
                    //TODO Buscar dados localmente
                    binding.swipeRefreshLayout.setRefreshing(false);
                    Snackbar.make(binding.swipeRefreshLayout, "Erro ao carregar a lista", Snackbar.LENGTH_LONG).show();
            }
            binding.swipeRefreshLayout.setOnRefreshListener(newsViewModel::findNews);

        });
        return root;
    }


    private void observeNews() {
        //set de um layout para minha recycler view
        //binding.swipeRefreshLayout.setRefreshing(true);
        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            //passa para meu adapter a lista a ser consumida
            binding.rvNews.setHasFixedSize(true);
            binding.rvNews.setAdapter(new NewsAdapter(news, updateNews -> {
                //evento de click no favoritos para salvar
                newsViewModel.saveNews(updateNews);

            }));
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void setupMatchsRefresh() {
        //TODO: Fazer a atualização das partidas no arrastar do Swipe
        binding.swipeRefreshLayout.setOnRefreshListener(this::setupMatchsRefresh);
    }

}