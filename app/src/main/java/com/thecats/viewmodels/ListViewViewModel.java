package com.thecats.viewmodels;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thecats.models.CatsImage;
import com.thecats.repositories.CatsImageRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewViewModel extends ViewModel {


    private CatsImageRepository catsImageRepository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<List<CatsImage>> datumMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    @Inject
    public ListViewViewModel(CatsImageRepository catsImageRepository) {
        this.catsImageRepository = catsImageRepository;
        this.isLoading.setValue(true);
    }

    public MutableLiveData<List<CatsImage>> getDatumMutableLiveData() {
        loadData();
        return datumMutableLiveData;
    }

    private void loadData() {
        disposable.add(catsImageRepository.modelSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<CatsImage>>() {
                                   @Override
                                   public void onSuccess(List<CatsImage> value) {
                                       isLoading.setValue(false);
                                       datumMutableLiveData.setValue(value);
                                   }

                                   @Override
                                   public void onError(Throwable e) {
                                       Log.e("api===error", e.getLocalizedMessage());
                                       isLoading.setValue(false);
                                   }
                               }


                )

        );

    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

    }
}
