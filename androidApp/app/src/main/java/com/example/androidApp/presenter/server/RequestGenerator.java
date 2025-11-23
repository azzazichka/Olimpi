package com.example.androidApp.presenter.server;

import android.widget.Toast;

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
                        Toast.makeText(mainActivity.getApplicationContext(), "Успешно", Toast.LENGTH_SHORT).show();
                        mainActivity.hideLoad();

                        if (onSuccessCallback != null) {
                            onSuccessCallback.accept(response);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Toast.makeText(mainActivity.getApplicationContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                        mainActivity.hideLoad();

                    }
                });
    }
}
