package com.dhara.gameoflife.utils.interfaces;

import com.dhara.gameoflife.model.BindableBoolean;

import rx.Observable;

public interface ComputationService {
    Observable<BindableBoolean[][]> getObservable(final BindableBoolean[][] cellStates);
}
