package com.snowprojects.sample.androidjsonparsing;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Snow Corp Team on 06/06/20 at 5:19 PM.
 * support@snowcorp.org
 * www.snowcorp.org
 */

public interface ApiService {
    String BASE_URL = "https://cloud.snowcorp.org/";

    @GET("demo/json-sample.php")
    Call<String> getMovies();
}
