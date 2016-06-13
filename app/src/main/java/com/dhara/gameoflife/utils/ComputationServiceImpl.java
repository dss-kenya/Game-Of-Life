package com.dhara.gameoflife.utils;

import com.dhara.gameoflife.manager.CellStateManager;
import com.dhara.gameoflife.model.BindableBoolean;
import com.dhara.gameoflife.utils.interfaces.ComputationService;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

public class ComputationServiceImpl implements ComputationService {
    @Override
    public Observable<BindableBoolean[][]> getObservable(final BindableBoolean[][] cellStates) {
        return Observable.interval(200, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, BindableBoolean[][]>() {
                    @Override
                    public BindableBoolean[][] call(Long aLong) {
                        return CellStateManager.performAnimation(cellStates,
                                cellStates.length, cellStates[0].length);
                    }
                });
    }
}
