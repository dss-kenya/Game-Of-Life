package com.dhara.gameoflife.utils;

import com.dhara.gameoflife.GameOfLifeApp;
import com.dhara.gameoflife.callbacks.IResponseListener;
import com.dhara.gameoflife.model.BindableBoolean;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class RxJavaUtils {
    private Observable<BindableBoolean[][]> mObservable;

    public RxJavaUtils(Observable<BindableBoolean[][]> observable) {
        mObservable = observable;
    }

    public Subscription getSubscription(IResponseListener listener) {
        return mObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(GameOfLifeApp.getAppContext().defaultSubscribeScheduler())
                .subscribe(getCalculationSubscriber(listener));
    }

    private Subscriber<BindableBoolean[][]> getCalculationSubscriber(final IResponseListener listener) {
        return new Subscriber<BindableBoolean[][]>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BindableBoolean[][] newCellStates) {
                if(listener != null) listener.onResponseReceived(newCellStates);
            }
        };
    }
}
