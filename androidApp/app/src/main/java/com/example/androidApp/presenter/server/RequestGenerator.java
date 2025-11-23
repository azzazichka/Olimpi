package com.example.androidApp.presenter.server;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androidApp.MainActivity;

import java.util.function.Consumer;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RequestGenerator {
    public static <T> Disposable makeApiCall(
            MainActivity mainActivity,
            @Nullable String onSuccessMessage,
            @Nullable String onErrorMessage,
            Single<T> apiCall,
            Consumer<T> onSuccessCallback) {

        return apiCall
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<T>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        mainActivity.showLoad();
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull T response) {
                        if (onSuccessMessage != null)
                            Toast.makeText(mainActivity.getApplicationContext(), onSuccessMessage, Toast.LENGTH_LONG).show();

                        mainActivity.hideLoad();

                        if (onSuccessCallback != null) {
                            onSuccessCallback.accept(response);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        if (onErrorMessage != null)
                            Toast.makeText(mainActivity.getApplicationContext(), onErrorMessage, Toast.LENGTH_LONG).show();
                        mainActivity.hideLoad();
                        Log.e("AZZA", e.toString());

                    }
                });
    }
}
