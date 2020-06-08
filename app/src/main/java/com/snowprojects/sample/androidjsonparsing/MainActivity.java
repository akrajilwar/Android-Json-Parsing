package com.snowprojects.sample.androidjsonparsing;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Snow Corp Team on 06/06/20 at 6:02 PM.
 * support@snowcorp.org
 * www.snowcorp.org
 */

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    private List<Movie> movieList;
    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressBar = findViewById(R.id.progress_bar);

        init();
    }

    private void init() {
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this, movieList);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(movieAdapter);

        loadData();
    }

    private void loadData() {
        if (Functions.isNetworkConnected(this)) {
            mProgressBar.setVisibility(View.VISIBLE);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiService.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            ApiService apiService = retrofit.create(ApiService.class);
            Call<String> call = apiService.getMovies();

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body());
                                JSONArray jsonArray = jsonObject.getJSONArray("movies");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jObj = jsonArray.getJSONObject(i);

                                    String title = jObj.getString("name");
                                    String imageUrl = jObj.getString("imageUrl");

                                    Movie item = new Movie();
                                    item.setName(title);
                                    item.setImage(imageUrl);

                                    movieList.add(item);

                                    mProgressBar.setVisibility(View.GONE);
                                }

                                movieAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, R.string.string_some_thing_wrong, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    mProgressBar.setVisibility(View.GONE);
                    Log.w(TAG, "loadData: failed!", t);
                    Toast.makeText(MainActivity.this, R.string.string_some_thing_wrong, Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, R.string.string_internet_connection_not_available, Toast.LENGTH_SHORT).show();
        }
    }
}