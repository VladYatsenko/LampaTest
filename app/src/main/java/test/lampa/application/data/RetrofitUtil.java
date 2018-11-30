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

    private ApiService apiService = retrofitClient.getClient().create(ApiService.class);;

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

//    public ApiService getApiService() {
//        if (apiService == null) {
//            apiService =
//        }
//        return apiService;
//    }

    public Single<NewsResponse> getNews(){
        return apiService.getNewsList().subscribeOn(Schedulers.io());
    }

}
