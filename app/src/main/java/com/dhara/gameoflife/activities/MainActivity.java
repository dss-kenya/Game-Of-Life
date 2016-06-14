package com.dhara.gameoflife.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.dhara.gameoflife.GameOfLifeApp;
import com.dhara.gameoflife.R;
import com.dhara.gameoflife.adapter.CellAdapter;
import com.dhara.gameoflife.databinding.ActivityMainBinding;
import com.dhara.gameoflife.manager.StaticManager;
import com.dhara.gameoflife.model.BindableBoolean;
import com.dhara.gameoflife.viewmodels.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private BindableBoolean mStates[][] = null;
    private CellAdapter mCellAdapter;
    private MainActivityViewModel mMainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStates = StaticManager.getInitialStates();

        mMainActivityViewModel =new MainActivityViewModel(mStates);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMainViewModel(mMainActivityViewModel);

        final GridLayoutManager layoutManager = new GridLayoutManager(GameOfLifeApp.getAppContext(),
                20, GridLayoutManager.VERTICAL, false);
        mCellAdapter = new CellAdapter(R.layout.individual_grid_row, mStates);
        binding.recyclerViewStates.setLayoutManager(layoutManager);
        binding.recyclerViewStates.setAdapter(mCellAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainActivityViewModel.onDestroy();
    }
}