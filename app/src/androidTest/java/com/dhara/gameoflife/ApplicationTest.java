package com.dhara.gameoflife;

import android.test.ApplicationTestCase;

import org.junit.After;
import org.junit.Test;

public class ApplicationTest extends ApplicationTestCase<GameOfLifeApp> {
    private GameOfLifeApp mApp;
    public ApplicationTest() {
        super(GameOfLifeApp.class);
    }

    @Test
    protected void setUp() throws Exception {
        super.setUp();
        createApplication();
        mApp = getApplication();
        assertNotNull(mApp);
    }

    @After
    public void tearDown() {
        terminateApplication();
    }
}