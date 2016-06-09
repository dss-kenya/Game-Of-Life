package com.dhara.gameoflife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private boolean state[][] = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        state = new boolean[][]
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
    }
}
