package test.lampa.application.data.web;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import test.lampa.BuildConfig;
import test.lampa.application.data.web.abstractions.BaseClient;

public class RetrofitClient extends BaseClient {
    private Context context;
    private String url;

    public RetrofitClient(Context context) {
        this.context = context;
        url= BuildConfig.BASE_URL;
    }

    public RetrofitClient(Context context, String url) {
        this.context = context;
        this.url=url;
    }

    @Override
    public OkHttpClient client() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Log.e("Logger", message));
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }




    @Override
    public String url() {
        return url;
    }

}
