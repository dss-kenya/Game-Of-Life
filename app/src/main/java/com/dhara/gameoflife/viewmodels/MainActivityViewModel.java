package com.dhara.gameoflife.viewmodels;

import android.databinding.ObservableInt;
import android.view.View;

import com.dhara.gameoflife.callbacks.IActivityMainClickHandlers;
import com.dhara.gameoflife.callbacks.IResponseListener;
import com.dhara.gameoflife.model.BindableBoolean;
import com.dhara.gameoflife.utils.ComputationServiceImpl;
import com.dhara.gameoflife.utils.RxJavaUtils;

import rx.Subscription;

public class MainActivityViewModel implements ViewModel, IActivityMainClickHandlers, IResponseListener {
    public ObservableInt tempViewVisibility;
    private BindableBoolean[][] mCellStates;
    private Subscription mSubscription;
    private IResponseListener mResponseListener;

    public MainActivityViewModel(BindableBoolean[][] cellStates) {
        mCellStates = cellStates;
        mResponseListener = this;
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
        RxJavaUtils rxJavaUtils = new RxJavaUtils(new ComputationServiceImpl().getObservable(mCellStates));
        mSubscription = rxJavaUtils.getSubscription(mResponseListener);
    }

    @Override
    public void onResponseReceived(BindableBoolean[][] newStates) {
        mCellStates = newStates;
    }

    @Override
    public void onError() {
    }
}
