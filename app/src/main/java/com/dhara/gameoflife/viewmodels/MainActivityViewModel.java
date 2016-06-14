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
        /**
         * When the click on the start button is triggered,
         * the temp layout is set to visible mode to prevent
         * user clicks on the cells
         */
        tempViewVisibility.set(View.VISIBLE);
        performOperations();
    }

    @Override
    public void onStopClicked(View view) {
        tempViewVisibility.set(View.GONE);

        /**
         * Un-subscribe the subscription
         */
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

    /**
     * For testing
     * @param subscription
     */
    public void setSubscription(Subscription subscription) {
        this.mSubscription = subscription;
    }

    /**
     * Performs the operations on the cells
     */
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
