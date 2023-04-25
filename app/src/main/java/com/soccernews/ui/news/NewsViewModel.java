package com.soccernews.ui.news;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.soccernews.domain.News;

import java.util.ArrayList;
import java.util.List;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<News>> mNews;

    public NewsViewModel() {
        this.mNews = new MutableLiveData<>();
        List<News> news = new ArrayList<>();
        news.add(new News("feroviaria", "Why do we use it?", " Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"));
        news.add(new News("santos", "Where can I get some?", " It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using"));
        news.add(new News("flamengo", "Where does it come from?", " There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the L"));

        this.mNews.setValue(news);
    }

    public MutableLiveData<List<News>> getNews() {

        return mNews;
    }
}