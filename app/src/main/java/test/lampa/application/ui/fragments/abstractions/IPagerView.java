package test.lampa.application.ui.fragments.abstractions;

import android.arch.lifecycle.LifecycleOwner;
import android.view.ViewGroup;

import java.util.List;

import test.lampa.application.ui.fragments.news.pager.model.NewsModel;

public interface IPagerView extends LifecycleOwner {

    void updateIndicator(ViewGroup indicatorsContainer, List<NewsModel> list);

    void updateNewsList(List<NewsModel> list);
}
