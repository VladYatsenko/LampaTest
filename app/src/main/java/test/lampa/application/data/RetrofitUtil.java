package test.lampa.application.data;

import android.content.Context;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import test.lampa.application.data.web.RetrofitClient;
import test.lampa.application.data.web.api.ApiService;
import test.lampa.application.data.web.model.NewsResponse;

public class RetrofitUtil {

    private static RetrofitUtil retrofitUtil;
    private static RetrofitClient retrofitClient;

    //private ApiService apiService = ;

    private static RetrofitUtil getInstance() {
        if (retrofitUtil == null) {
            retrofitUtil = new RetrofitUtil();
        }
        return retrofitUtil;
    }

    public static RetrofitUtil getMainInstance(Context context) {
        if (retrofitClient == null) {
            retrofitClient = new RetrofitClient(context);
        }
        return getInstance();
    }


    public Single<NewsResponse> getNews(){
        return retrofitClient.getClient().create(ApiService.class).getNewsList().subscribeOn(Schedulers.io());
    }

    public Single<NewsResponse> getNextNewsList(String cursor){
        return retrofitClient.getClient().create(ApiService.class).getNextNewsList(cursor, "json").subscribeOn(Schedulers.io());
    }

}
