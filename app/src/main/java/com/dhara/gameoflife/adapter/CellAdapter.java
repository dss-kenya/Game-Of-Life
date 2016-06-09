package com.dhara.gameoflife.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class CellAdapter extends BaseAdapter {
    private final Context mContext;
    private int totalCount;
    private int rowPosition;
    private int columnPosition;
    private boolean mCellStates[][];

    public CellAdapter(Context context, boolean states[][]) {
        mContext = context;
        rowPosition = 0;
        columnPosition = 0;
        mCellStates = states;
        totalCount = getColumnCount() * getRowCount();
    }

    @Override
    public int getCount() {
        return totalCount;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = new TextView(mContext);
        }else {
            textView = (TextView) convertView;
        }
        columnPosition = position % mCellStates[0].length;
        rowPosition = (position - columnPosition)/mCellStates[0].length;
        textView.setTextColor(mCellStates[rowPosition][columnPosition] ?
                Color.parseColor("#000000") : Color.parseColor("#00000000"));
        return textView;
    }

    public int getRowCount() {
        return mCellStates.length;
    }

    public int getColumnCount(){
        return mCellStates[0].length;
    }

    public Object getItem(int rowNum, int columnNum) {
        return mCellStates[rowNum][columnNum];
    }

    public void setStates(boolean[][] states) {
        mCellStates = states;
    }
}
