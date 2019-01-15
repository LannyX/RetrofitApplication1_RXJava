package com.lanny.retrofitapplication.network;

//END POINT

import com.lanny.retrofitapplication.model.Photo;

import java.util.List;

import retrofit2.http.GET;

public interface GetDataService {

    @GET("/photos")
    io.reactivex.Observable<List<Photo>> getAllPhotos();

}
