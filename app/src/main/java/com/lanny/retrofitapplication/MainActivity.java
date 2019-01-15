package com.lanny.retrofitapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.lanny.retrofitapplication.model.Photo;
import com.lanny.retrofitapplication.network.GetDataService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    final String TAG = MainActivity.class.getSimpleName();
    Retrofit retrofit;
    Button button;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        button = findViewById(R.id.button2);
        recyclerView = findViewById(R.id.recyclerView);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.create(GetDataService.class)
                .getAllPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError );

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //add rxjava with retrofit

            }
        });

//                Call<List<Photo>> call = getDataService.getAllPhotos();
//                call.enqueue(new Callback<List<Photo>>() {
//                    @Override
//                    public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
//                        Log.i("xxx", response.toString());
//                        callPhotoAdapter(response.body());
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Photo>> call, Throwable t) {
//                        Log.i("xxx", t.getMessage());
//                    }
//                });
//            }
//        });

//    private void callPhotoAdapter(List<Photo> body) {
//        myAdapter = new MyAdapter(body);
//        recyclerView.setAdapter(myAdapter);
//    }

    }

    private void handleResults(List<Photo> photos) {
        Log.i(TAG, photos.get(1).getTitle());
    }


    private void handleError(Throwable t) {
        Log.i(TAG, t.getMessage());
    }
}
