package com.thecats.repositories;

import com.thecats.models.CatsImage;
import com.thecats.remote.CatImagesService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CatsImageRepository {
    private CatImagesService mCatImagesService;

    @Inject
    public CatsImageRepository(CatImagesService mCatImagesService) {
        this.mCatImagesService = mCatImagesService;
    }

    public Single<List<CatsImage>> modelSingle()
    {
        return mCatImagesService.getCatsImagesModel(50);
    }
}
