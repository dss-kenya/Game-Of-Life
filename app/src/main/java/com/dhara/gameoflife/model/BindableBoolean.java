package com.dhara.gameoflife.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.dhara.gameoflife.BR;

public class BindableBoolean extends BaseObservable {
    boolean value;

    public BindableBoolean(boolean state) {
        value = state;
        notifyPropertyChanged(BR.value);
    }

    @Bindable
    public void setValue(boolean value) {
        this.value = value;
        notifyPropertyChanged(BR.value);
    }

    @Bindable
    public boolean isValue() {
        return value;
    }
}
