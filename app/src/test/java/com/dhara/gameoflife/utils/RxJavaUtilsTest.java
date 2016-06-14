package com.dhara.gameoflife.utils;

import com.dhara.gameoflife.GameOfLifeApp;
import com.dhara.gameoflife.callbacks.IResponseListener;
import com.dhara.gameoflife.mock.MockStates;
import com.dhara.gameoflife.model.BindableBoolean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import rx.Subscription;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class RxJavaUtilsTest {
    private IResponseListener mListener;
    private GameOfLifeApp mMockedApp;
    private TestSubscriber<BindableBoolean[][]> mTestSubscriber;

    @Before
    public void setUp() {
        mMockedApp = mock(GameOfLifeApp.class);
        mListener = mock(IResponseListener.class);
        mTestSubscriber = new TestSubscriber<>();
        mMockedApp.onCreate();
        mMockedApp.setDefaultSubscribeScheduler(Schedulers.io());
    }

    @Test
    @PrepareForTest({RxJavaUtils.class})
    public void testGetSubscription() throws Exception{
        RxJavaUtils rxJavaUtils = mock(RxJavaUtils.class);
        PowerMockito.whenNew(RxJavaUtils.class)
                .withArguments(new ComputationServiceImpl().getObservable(MockStates.getInitialState()))
                .thenReturn(rxJavaUtils);

        Subscription mockedSubscription = mock(RxJavaUtils.class).getSubscription(mListener);
        Subscription subscription = new ComputationServiceImpl().getObservable(MockStates.getInitialState())
                .subscribe(mTestSubscriber);

        assertEquals(mockedSubscription, subscription);
    }
}
