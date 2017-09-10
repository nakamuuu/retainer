package net.divlight.retainer.sample;

import android.content.Context;
import android.os.RemoteException;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    private static final long WAIT_FOR_WINDOW_UPDATE_TIMEOUT = 5000;

    private Context context;
    private UiDevice device;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws RemoteException {
        context = getInstrumentation().getTargetContext();
        device = UiDevice.getInstance(getInstrumentation());
        device.unfreezeRotation();
    }

    @Test
    public void testRetainByActivity() throws RemoteException {
        onView(withId(R.id.text_view)).check(matches(withText(context.getString(R.string.value_format, 1))));

        device.setOrientationLeft();
        device.waitForWindowUpdate(null, WAIT_FOR_WINDOW_UPDATE_TIMEOUT);
        device.waitForIdle();
        onView(withId(R.id.text_view)).check(matches(withText(context.getString(R.string.value_format, 2))));

        device.setOrientationNatural();
        device.waitForWindowUpdate(null, WAIT_FOR_WINDOW_UPDATE_TIMEOUT);
        device.waitForIdle();
        onView(withId(R.id.text_view)).check(matches(withText(context.getString(R.string.value_format, 3))));

        device.setOrientationRight();
        device.waitForWindowUpdate(null, WAIT_FOR_WINDOW_UPDATE_TIMEOUT);
        device.waitForIdle();
        onView(withId(R.id.text_view)).check(matches(withText(context.getString(R.string.value_format, 4))));

        device.setOrientationNatural();
        device.waitForWindowUpdate(null, WAIT_FOR_WINDOW_UPDATE_TIMEOUT);
        device.waitForIdle();
        onView(withId(R.id.text_view)).check(matches(withText(context.getString(R.string.value_format, 5))));
    }

    @Test
    public void testRetainByDialogFragment() throws RemoteException {
        onView(withId(R.id.button)).perform(click());
        device.waitForWindowUpdate(null, WAIT_FOR_WINDOW_UPDATE_TIMEOUT);
        device.waitForIdle();
        onView(withId(android.R.id.message)).check(matches(withText(context.getString(R.string.value_format, 1))));

        device.setOrientationLeft();
        device.waitForWindowUpdate(null, WAIT_FOR_WINDOW_UPDATE_TIMEOUT);
        device.waitForIdle();
        onView(withId(android.R.id.message)).check(matches(withText(context.getString(R.string.value_format, 2))));

        device.setOrientationNatural();
        device.waitForWindowUpdate(null, WAIT_FOR_WINDOW_UPDATE_TIMEOUT);
        device.waitForIdle();
        onView(withId(android.R.id.message)).check(matches(withText(context.getString(R.string.value_format, 3))));

        device.setOrientationRight();
        device.waitForWindowUpdate(null, WAIT_FOR_WINDOW_UPDATE_TIMEOUT);
        device.waitForIdle();
        onView(withId(android.R.id.message)).check(matches(withText(context.getString(R.string.value_format, 4))));

        device.setOrientationNatural();
        device.waitForWindowUpdate(null, WAIT_FOR_WINDOW_UPDATE_TIMEOUT);
        device.waitForIdle();
        onView(withId(android.R.id.message)).check(matches(withText(context.getString(R.string.value_format, 5))));

        device.pressBack();
        device.waitForWindowUpdate(null, WAIT_FOR_WINDOW_UPDATE_TIMEOUT);
        device.waitForIdle();

        onView(withId(R.id.button)).perform(click());
        device.waitForWindowUpdate(null, WAIT_FOR_WINDOW_UPDATE_TIMEOUT);
        device.waitForIdle();
        onView(withId(android.R.id.message)).check(matches(withText(context.getString(R.string.value_format, 1))));
    }
}

