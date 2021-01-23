package com.thecats.remote;

import com.thecats.models.CatsImage;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CatImagesService {

    @GET("images/search")
    Single<List<CatsImage>> getCatsImagesModel(@Query("limit") int limit);
}
