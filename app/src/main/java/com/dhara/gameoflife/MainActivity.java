package com.dhara.gameoflife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.dhara.gameoflife.adapter.CellAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private boolean mStates[][] = null;
    private CellAdapter mCellAdapter;
    private GridView mGridView;
    private Button mBtnStart;
    private Button mBtnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setConfig();
    }

    private void init() {
        mStates = new boolean[][]
                {
                        {false,false,false,false,false,false,false,false,false,false},
                        {false,false,false,false,false,false,false,false,false,false},
                        {false,false,false,false,false,false,false,false,false,false},
                        {false,false,false,false,false,false,false,false,false,false},
                        {false,false,false,false,false,false,false,false,false,false},
                        {false,false,false,false,false,false,false,false,false,false},
                        {false,false,false,false,false,false,false,false,false,false},
                        {false,false,false,false,false,false,false,false,false,false},
                        {false,false,false,false,false,false,false,false,false,false},
                        {false,false,false,false,false,false,false,false,false,false},
                };

        mGridView = (GridView)findViewById(R.id.grid_view);
        mBtnStart = (Button)findViewById(R.id.btn_start);
        mBtnStop = (Button)findViewById(R.id.btn_stop);

        mCellAdapter = new CellAdapter(MainActivity.this, mStates);
        mGridView.setAdapter(mCellAdapter);

        mBtnStart.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mGridView.setOnItemClickListener(this);
    }

    private void setConfig() {
        for(int i=0;i<mStates.length;i++)
            setConfigForRows(mStates[i],0.10f);
    }

    private void setConfigForRows(boolean[] rows, float percentToFill) {
        for(int i=0;i< rows.length;i++) {
            if(Math.random() < percentToFill)
                rows[i]=true;
            else
                rows[i]=false;
        }
        mCellAdapter.setStates(mStates);
        mCellAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                boolean[][] newStates =
                        performAnimation(mStates,mStates.length,mStates[0].length);
                mStates = newStates;
                mCellAdapter.setStates(mStates);
                mCellAdapter.notifyDataSetChanged();
                break;

            case R.id.btn_stop:

                break;
        }
    }

    private boolean[][] performAnimation(boolean[][] states, int rows, int cols) {
        boolean[][] newStates = new boolean[rows][cols];
        for(int i=0;i<rows;i++)
            for(int j=0;j<cols;j++) {
                newStates[i][j] = flip(states, i, j, rows, cols);
            }
        return newStates;
    }

    private boolean flip(boolean[][] states, int i, int j, int rows, int cols) {
        int count = around(states,i,j, rows, cols);
        if(states[i][j]) {
            if(count<2||count>3)
                return false;
            return true;
        } else {
            if(count==3)
                return true;
            return false;
        }
    }

    private int around(boolean[][] states, int i, int j, int rows, int cols) {
        int count=0;
        for(int x=i-1;x<=i+1;x++)
            for(int y=j-1;y<=j+1;y++)
            {
                if(x==i&&y==j)
                    continue;
                count += eval(states,x,y, rows, cols);
            }
        return count;
    }

    private int eval(boolean[][] states, int i, int j, int rows, int cols) {
        if(i<0||j<0||i==rows||j==cols)
            return 0;
        if(states[i][j])
            return 1;
        return 0;
    }
}