package com.dhara.gameoflife.callbacks;

import com.dhara.gameoflife.model.BindableBoolean;

public interface IResponseListener {
    void onResponseReceived(BindableBoolean[][] newStates);
    void onError();
}
