package com.dhara.gameoflife.manager;

import com.dhara.gameoflife.mock.MockStates;
import com.dhara.gameoflife.model.BindableBoolean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotSame;

@RunWith(PowerMockRunner.class)
public class CellStateManagerTest {
    private BindableBoolean[][] mCellStates;
    private BindableBoolean[][] mNewStates;

    @Before
    public void setUp(){
        PowerMockito.mockStatic(StaticManager.class);
        mCellStates = StaticManager.getInitialStates();
        mNewStates = MockStates.getAnimatedState();
    }

    @Test
    @PrepareForTest({CellStateManager.class})
    public void testPerformAnimation() {
        PowerMockito.mockStatic(CellStateManager.class);
        PowerMockito.when(CellStateManager.performAnimation(mCellStates,
                mCellStates.length, mCellStates[0].length)).thenReturn(mNewStates);

        BindableBoolean[][] newStates = CellStateManager.performAnimation(mCellStates,
                mCellStates.length, mCellStates[0].length);

        assertArrayEquals(newStates, mNewStates);
        assertNotSame(mCellStates, mNewStates);
    }
}
