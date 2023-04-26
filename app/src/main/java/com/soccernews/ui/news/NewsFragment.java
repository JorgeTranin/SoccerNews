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

import com.soccernews.MainActivity;
import com.soccernews.databinding.FragmentNewsBinding;
import com.soccernews.ui.news.adapternews.NewsAdapter;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NewsViewModel newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        loadNews(newsViewModel);


        return root;
    }


    private void loadNews(NewsViewModel newsViewModel) {
        //set de um layout para minha recycler view
        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            //passa para meu adapter a lista a ser consumida
            binding.rvNews.setHasFixedSize(true);
            binding.rvNews.setAdapter(new NewsAdapter(news, updateNews -> {
                //evento de click no favoritos para salvar
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    activity.getDb().newsDao().save(updateNews);
                }

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
        //binding.swiperefreshlayout.setOnRefreshListener(this::findMatchesFromApi);
    }

}