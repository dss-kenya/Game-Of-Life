package com.dhara.gameoflife;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import rx.Scheduler;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GameOfLifeApp.class })
public class GameOfLifeAppTest {
    private GameOfLifeApp mApp;
    private Context mContext;
    private Scheduler defaultSubscribeScheduler;

    @Before
    public void setUp(){
        mContext = Mockito.mock(GameOfLifeApp.class);
        mApp = Mockito.mock(GameOfLifeApp.class);
        mApp.setDefaultSubscribeScheduler(Schedulers.io());
        defaultSubscribeScheduler = Schedulers.io();
    }

    @Test
    public void testGetAppContext() {
        // mocking the static method
        PowerMockito.mockStatic(GameOfLifeApp.class);
        PowerMockito.when(GameOfLifeApp.getAppContext()).thenReturn(mApp);
        assertNotNull(mContext);
        assertNotNull(mApp);
    }

    @Test
    public void testSubscriberScheduler() {
        Mockito.when(mApp.defaultSubscribeScheduler()).thenReturn(Schedulers.io());
        assertEquals(defaultSubscribeScheduler, mApp.defaultSubscribeScheduler());
        assertNotNull(mApp.defaultSubscribeScheduler());
    }
}
