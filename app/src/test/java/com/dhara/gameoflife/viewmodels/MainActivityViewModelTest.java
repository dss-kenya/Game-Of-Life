package com.dhara.gameoflife.viewmodels;

import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;

import com.dhara.gameoflife.GameOfLifeApp;
import com.dhara.gameoflife.callbacks.IActivityMainClickHandlers;
import com.dhara.gameoflife.manager.StaticManager;
import com.dhara.gameoflife.model.BindableBoolean;
import com.dhara.gameoflife.utils.ComputationServiceImpl;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.powermock.api.mockito.PowerMockito;

import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

@SmallTest
@RunWith(JUnit4.class)
public class MainActivityViewModelTest extends Assert{
    private MainActivityViewModel mainActivityViewModel;
    private TestSubscriber<BindableBoolean[][]> testSubscriber;
    private IActivityMainClickHandlers mockedClickListeners;
    private ComputationServiceImpl mockedComputationServiceImpl;
    private GameOfLifeApp mockedApp;

    @Before
    public void setup() {
        PowerMockito.mock(StaticManager.class);
        mockedClickListeners = mock(IActivityMainClickHandlers.class);
        mockedApp = mock(GameOfLifeApp.class);
        mainActivityViewModel = new MainActivityViewModel(StaticManager.getInitialStates());
        testSubscriber = new TestSubscriber<>();
        mockedComputationServiceImpl = new ComputationServiceImpl();
        mockedApp.setDefaultSubscribeScheduler(Schedulers.io());
    }

    @Test
    public void testOnStartClicked() {
        assertNotNull(mainActivityViewModel.tempViewVisibility);
        assertNotNull(mockedClickListeners);

        mainActivityViewModel.onStartClicked(null);

        assertThat(mainActivityViewModel.tempViewVisibility.get(),equalTo(View.VISIBLE));

        Observable<BindableBoolean[][]> observable =
                mockedComputationServiceImpl.getObservable(StaticManager.getInitialStates());
        assertNotNull(observable);
        observable.subscribe(testSubscriber);

        List<BindableBoolean[][]> newStates = testSubscriber.getOnNextEvents();

        testSubscriber.assertNoErrors();

        assertNotNull(newStates);
        testSubscriber.unsubscribe();
    }

    @Test
    public void testOnStopClicked() {
        Observable<BindableBoolean[][]> observable =
                mockedComputationServiceImpl.getObservable(StaticManager.getInitialStates());

        observable.subscribe(testSubscriber);

        assertNotNull(testSubscriber);
        testSubscriber.unsubscribe();

        testSubscriber.assertUnsubscribed();
        assertThat(mainActivityViewModel.tempViewVisibility.get(), equalTo(View.GONE));
    }

    @Test
    public void testSetSubscription() {
        mainActivityViewModel.setSubscription(testSubscriber);
    }

    @Test
    public void testOnDestroy() {
        mainActivityViewModel.setSubscription(testSubscriber);
        assertNotNull(testSubscriber);
        testSubscriber.unsubscribe();
        testSubscriber.assertUnsubscribed();
    }
}
