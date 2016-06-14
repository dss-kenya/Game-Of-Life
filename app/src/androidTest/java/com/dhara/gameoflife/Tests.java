package com.dhara.gameoflife;

import com.dhara.gameoflife.acitivities.MainActivityTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({ApplicationTest.class,
        MainActivityTest.class})
public class Tests {}
