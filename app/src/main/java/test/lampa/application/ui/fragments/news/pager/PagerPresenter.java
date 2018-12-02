package test.lampa.application.ui.fragments.news.pager;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
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
    private List<NewsModel> topNewsList = new ArrayList<>();


    private String nextListNews;

    PagerPresenter(Context context){
        this.context = context;
    }

    @Override
    public void attachView(IPagerView view) {
        super.attachView(view);
        responseTopNews();
        responseNewsList();
    }

    private void responseTopNews(){
        disposables.add(RetrofitUtil.getMainInstance(context)
                .getNews()
                .map(list->{
                    topNewsList.clear();
                    for (Results item : list.getResults()) {
                        topNewsList.add(
                                new NewsModel(
                                        item.getName(),
                                        item.getImage().getUrl(),
                                        item.getId(),
                                        item.getPrice()
                                ));
                    }
                    topNewsList.get(0).setSelected(true);
                    return topNewsList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list->{
                    Log.i(getClass().getSimpleName(), String.valueOf(list.size()));
                    view.updateHeader(list);
                })
        );
    }

    private void responseNewsList() {
        disposables.add(RetrofitUtil.getMainInstance(context)
                .getNews()
                .map(list->{
                    nextListNews = list.getNext();
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
                .subscribe(list->{
                    view.updateNewsList(list);
                })
        );
    }


    public void updateIndicator(ViewGroup indicatorsContainer, int position) {
        for (NewsModel item : topNewsList){
            item.setSelected(false);
        }
        topNewsList.get(position).setSelected(true);
        view.updateIndicator(indicatorsContainer, topNewsList);
    }

    public void updateIndicator(ViewGroup viewGroup){
        view.updateIndicator(viewGroup, topNewsList);
    }

    public void loadNext() {
        if(nextListNews!=null) {
            disposables.add(RetrofitUtil.getMainInstance(context)
                    .getNextNewsList(Uri.parse(nextListNews).getQueryParameter("cursor"))
                    .map(list -> {
                        nextListNews = list.getNext();
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
    }
}
