package com.soccernews.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.soccernews.databinding.FragmentFavoritesBinding;
import com.soccernews.ui.news.adapternews.NewsAdapter;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;
    private FavoritesViewModel favoritesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadFavoritesNews();

        return root;

    }

    private void loadFavoritesNews() {
        favoritesViewModel.loadFavoriteNews().observe(getViewLifecycleOwner(), localNews -> {
            binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
            //passa para meu adapter a lista a ser consumida
            binding.rvNews.setHasFixedSize(true);
            binding.rvNews.setAdapter(new NewsAdapter(localNews, updateNews -> {
                favoritesViewModel.saveNews(updateNews);
                loadFavoritesNews();

            }));
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}