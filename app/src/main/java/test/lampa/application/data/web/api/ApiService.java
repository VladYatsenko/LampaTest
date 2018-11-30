package test.lampa.application.data.web.api;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import test.lampa.application.data.web.model.NewsResponse;

public interface ApiService {

    @GET ("api/v1.0/products/?format=json")
    Single<NewsResponse> getNewsList();

}
