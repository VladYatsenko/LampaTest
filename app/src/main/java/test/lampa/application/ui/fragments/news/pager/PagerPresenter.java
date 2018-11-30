package test.lampa.application.ui.fragments.news.pager;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import test.lampa.application.data.RetrofitUtil;
import test.lampa.application.data.web.model.Results;
import test.lampa.application.ui.abstractions.NewsPresenter;
import test.lampa.application.ui.fragments.abstractions.IPagerView;
import test.lampa.application.ui.fragments.news.pager.model.NewsModel;

class PagerPresenter extends NewsPresenter<IPagerView>{

    private Context context;
    private List<NewsModel> newsList = new ArrayList<>();


    PagerPresenter(Context context){
        this.context = context;
    }

    @Override
    public void attachView(IPagerView view) {
        super.attachView(view);
        responseNewsList();
    }

    private void responseNewsList() {
        disposables.add(RetrofitUtil.getMainInstance(context)
                .getNews()
                .map(list->{
                    for (Results item : list.getResults()) {
                        newsList.add(
                                new NewsModel(
                                        item.getName(),
                                        item.getImage().getUrl(),
                                        item.getId(),
                                        item.getPrice()
                                ));
                    }
                    return newsList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::updateNewsList)
        );
    }

    public void updateIndicator(ViewGroup indicatorsContainer, int position) {
        for (NewsModel item : newsList){
            item.setSelected(false);
        }
        newsList.get(position).setSelected(true);
        view.updateIndicator(indicatorsContainer, newsList);
    }
}
