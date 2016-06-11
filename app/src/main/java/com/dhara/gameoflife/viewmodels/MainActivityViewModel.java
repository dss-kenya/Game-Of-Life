package com.dhara.gameoflife.viewmodels;

import android.databinding.ObservableInt;
import android.view.View;

import com.dhara.gameoflife.GameOfLifeApp;
import com.dhara.gameoflife.callbacks.IActivityMainClickHandlers;
import com.dhara.gameoflife.manager.CellStateManager;
import com.dhara.gameoflife.model.BindableBoolean;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class MainActivityViewModel implements ViewModel, IActivityMainClickHandlers {
    public ObservableInt tempViewVisibility;
    private BindableBoolean[][] mCellStates;
    private Subscription mSubscription;

    public MainActivityViewModel(BindableBoolean[][] cellStates) {
        mCellStates = cellStates;
        tempViewVisibility = new ObservableInt(View.GONE);
    }

    @Override
    public void onStartClicked(View view) {
        tempViewVisibility.set(View.VISIBLE);
        performOperations();
    }

    @Override
    public void onStopClicked(View view) {
        tempViewVisibility.set(View.GONE);

        if(mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void onDestroy() {
        if(mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    private void performOperations() {
        Observable<BindableBoolean[][]> observable =
                Observable.interval(200,TimeUnit.MILLISECONDS)
                        .map(new Func1<Long, BindableBoolean[][]>() {
                            @Override
                            public BindableBoolean[][] call(Long aLong) {
                                return CellStateManager.performAnimation(mCellStates,
                                        mCellStates.length, mCellStates[0].length);
                            }
                        });

        mSubscription = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(GameOfLifeApp.getAppContext().defaultSubscribeScheduler())
                .subscribe(new Subscriber<BindableBoolean[][]>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BindableBoolean[][] newCellStates) {
                        mCellStates = newCellStates;
                    }
                });
    }
}
