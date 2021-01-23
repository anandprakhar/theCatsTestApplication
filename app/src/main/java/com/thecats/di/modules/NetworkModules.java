package com.thecats.di.modules;

import com.thecats.remote.CatImagesService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@Module (includes = {ViewModelModule.class, ListViewModelModule.class, GridModelModule.class})
public abstract class NetworkModules {
    @Provides
    @Singleton
    static Retrofit provideRetrofit()
    {
        return new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    static CatImagesService provideCatsImageService(Retrofit retrofit)
    {
       return retrofit.create(CatImagesService.class);
    }
}
