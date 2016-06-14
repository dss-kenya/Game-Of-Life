package com.dhara.gameoflife.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dhara.gameoflife.BR;
import com.dhara.gameoflife.adapter.viewholder.StateViewHolder;
import com.dhara.gameoflife.callbacks.ICellClickHandlers;
import com.dhara.gameoflife.model.BindableBoolean;

public class CellAdapter extends RecyclerView.Adapter<StateViewHolder> {
    private final int RESOURCE;
    private int totalCount;
    private int rowPosition;
    private int columnPosition;
    private BindableBoolean mCellStates[][];

    /**
     * Constructor
     * @param resource
     * @param states
     */
    public CellAdapter(int resource, BindableBoolean states[][]) {
        RESOURCE = resource;
        rowPosition = 0;
        columnPosition = 0;
        mCellStates = states;
        totalCount = getColumnCount() * getRowCount();
    }


    @Override
    public StateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                RESOURCE, parent, false);
        return new StateViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(StateViewHolder holder, final int position) {
        holder.getBindingView().setVariable(BR.cellState,getItem(position));
        holder.getBindingView().setVariable(BR.handler, new ICellClickHandlers() {
            @Override
            public void onItemClicked(View view) {
                int rowPosition = getRowPosition(position);
                int columnPosition = getColumnPosition(position);
                mCellStates[rowPosition][columnPosition].setValue(!mCellStates[rowPosition][columnPosition].isValue());
            }
        });
        holder.getBindingView().executePendingBindings();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return totalCount;
    }

    /**
     * Get the position from the 2D Array and display it as a 1D array
     * @param position
     * @return
     */
    public BindableBoolean getItem(int position) {
        columnPosition = getColumnPosition(position);
        rowPosition = getRowPosition(position);
        return getItem(rowPosition, columnPosition);
    }

    public int getRowCount() {
        return mCellStates.length;
    }

    public int getColumnCount(){
        return mCellStates[0].length;
    }

    public BindableBoolean getItem(int rowNum, int columnNum) {
        return mCellStates[rowNum][columnNum];
    }

    public int getColumnPosition(int position) {
        return position % mCellStates[0].length;
    }

    public int getRowPosition(int position) {
        return (position - getColumnPosition(position))/mCellStates[0].length;
    }
}
