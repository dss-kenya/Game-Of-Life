package com.dhara.gameoflife.manager;

import com.dhara.gameoflife.model.BindableBoolean;

public class CellStateManager {
    public static BindableBoolean[][] performAnimation(BindableBoolean[][] cellStates, int rows, int cols) {
        for(int i=0;i<rows;i++)
            for(int j=0;j<cols;j++) {
                cellStates[i][j].setValue(flip(cellStates, i, j, rows, cols));
            }
        return cellStates;
    }

    private static boolean flip(BindableBoolean[][] states, int i, int j, int rows, int cols) {
        int count = around(states,i,j, rows, cols);
        if(states[i][j].isValue()) {
            if(count<2||count>3)
                return false;
            return true;
        } else {
            if(count==3)
                return true;
            return false;
        }
    }

    private static int around(BindableBoolean[][] states, int i, int j, int rows, int cols) {
        int count=0;
        for(int x=i-1;x<=i+1;x++)
            for(int y=j-1;y<=j+1;y++) {
                if(x==i&&y==j)
                    continue;
                count += evaluate(states,x,y, rows, cols);
            }
        return count;
    }

    private static int evaluate(BindableBoolean[][] states, int i, int j, int rows, int cols) {
        if(i<0||j<0||i==rows||j==cols)
            return 0;
        if(states[i][j].isValue())
            return 1;
        return 0;
    }
}
