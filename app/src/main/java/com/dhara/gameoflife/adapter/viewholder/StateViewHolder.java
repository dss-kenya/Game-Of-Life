package com.dhara.gameoflife.adapter.viewholder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public class StateViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding bindingView;

    public StateViewHolder(ViewDataBinding bindingView) {
        super(bindingView.getRoot());
        this.bindingView = bindingView;
        this.bindingView.executePendingBindings();
    }

    public ViewDataBinding getBindingView() {
        return bindingView;
    }
}
