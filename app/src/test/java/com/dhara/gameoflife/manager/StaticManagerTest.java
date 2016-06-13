package com.dhara.gameoflife.manager;

import com.dhara.gameoflife.mock.MockStates;
import com.dhara.gameoflife.model.BindableBoolean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class StaticManagerTest {
    private BindableBoolean[][] mCellStates;

    @Before
    public void setUp(){
        mCellStates = MockStates.getInitialState();
    }

    @PrepareForTest({StaticManager.class})
    @Test
    public void testGetInitialStates(){
        PowerMockito.mockStatic(StaticManager.class);
        PowerMockito.when(StaticManager.getInitialStates()).thenReturn(mCellStates);

        BindableBoolean[][] initialCellStates = StaticManager.getInitialStates();

        assertEquals(initialCellStates, mCellStates);
        assertEquals(20,initialCellStates.length);
        assertEquals(20, initialCellStates[0].length);
    }
}
