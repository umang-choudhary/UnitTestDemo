package com.example.unittestdemo.view

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.unittestdemo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testAddAndViewEmployee() {
        onView(withId(R.id.btn_add_new_employee)).perform(ViewActions.click())

        val name = "Bhavish"
        val email = "bhavish@gmail.com"
        val pinCode = "201009"

        onView(withId(R.id.edit_text_user_name))
            .perform(ViewActions.typeText(name))
        onView(withId(R.id.edit_text_email))
            .perform(ViewActions.typeText(email))
        onView(withId(R.id.edit_text_pin_code))
            .perform(ViewActions.typeText(pinCode))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btn_save)).perform(ViewActions.click())

        // Assertions
        onView(withText(name)).check(matches(isDisplayed()))
        onView(withText(email)).check(matches(isDisplayed()))
        onView(withText(pinCode)).check(matches(isDisplayed()))
    }
}