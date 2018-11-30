package test.lampa.application.data.web.abstractions;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseClient {

    public abstract OkHttpClient client();

    public abstract String url();

    public Retrofit getClient() {

        return new Retrofit.Builder()
                .baseUrl(url())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client())
                .build();

    }
}
