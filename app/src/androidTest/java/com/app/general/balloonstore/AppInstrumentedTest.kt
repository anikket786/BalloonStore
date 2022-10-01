package com.app.general.balloonstore

import android.support.test.uiautomator.UiDevice
import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.app.general.balloonstore.presentation.balloons.ui.BalloonsActivity
import com.app.general.balloonstore.presentation.balloons.ui.viewholders.BalloonViewHolder
import junit.framework.AssertionFailedError
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeoutException


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AppInstrumentedTest {
    private lateinit var mUiDevice: UiDevice

    @get:Rule
    val balloonsActivityRule: ActivityScenarioRule<BalloonsActivity> =
        ActivityScenarioRule(BalloonsActivity::class.java)

    @Before
    fun setup() {
        mUiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    /**
     * Test app's normal flow when the connectivity requirements are met
     */
    @Test
    fun testAppFlowA() {
        Espresso.onView(isRoot()).perform(waitFor(5000))

        Espresso.onView(withId(R.id.rvBalloonList))
            .waitUntilVisible(5000)
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<BalloonViewHolder>(
                    0,
                    ViewActions.click()
                )
            )

        Espresso.onView(withId(R.id.viewPager))
            .waitUntilVisible(5000)
            .check(matches(isDisplayed()))

        mUiDevice.pressBack()

        Espresso.onView(withId(R.id.item_menu_my_balloons))
            .waitUntilVisible(1000)
            .perform(ViewActions.click())

        Espresso.onView(withId(R.id.rvFavorites))
            .waitUntilVisible(5000)
            .check(matches(isDisplayed()))

        // ToDo: Need to cover all the ui test cases
    }

    /**
     * Wait for view to be visible
     */
    private fun ViewInteraction.waitUntilVisible(timeout: Long): ViewInteraction {
        val startTime = System.currentTimeMillis()
        val endTime = startTime + timeout

        do {
            try {
                check(matches(isDisplayed()))
                return this
            } catch (e: AssertionFailedError) {
                Thread.sleep(50)
            }
        } while (System.currentTimeMillis() < endTime)

        throw TimeoutException()
    }

    /**
     * Perform action of waiting for a specific time.
     */
    private fun waitFor(millis: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "Wait for $millis milliseconds."
            }

            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadForAtLeast(millis)
            }
        }
    }
}