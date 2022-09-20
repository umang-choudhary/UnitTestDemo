package com.example.unittestdemo.view

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.unittestdemo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat


@RunWith(AndroidJUnit4::class)
class AddEmployeeActivityTest {
    private lateinit var scenario: ActivityScenario<AddEmployeeActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(AddEmployeeActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testAddEmployee() {
        val name = "Bhavish"
        val email = "bhavish@gmail.com"
        val pinCode = "201009"

        onView(withId(R.id.edit_text_user_name)).perform(typeText(name))
        onView(withId(R.id.edit_text_email)).perform(typeText(email))
        onView(withId(R.id.edit_text_pin_code)).perform(typeText(pinCode))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btn_save)).perform(click())
        assertThat(onView(withId(R.id.text_view_success_msg)).check(matches(withText("Employee Added Successfully"))))
    }

}