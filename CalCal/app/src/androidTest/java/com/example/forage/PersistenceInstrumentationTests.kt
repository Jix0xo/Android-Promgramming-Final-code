package com.example.forage

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class dPersistenceInstrumentationTests {

    @Before
    fun setup() {
        launchActivity<MainActivity>()
        onView(withId(R.id.add_forageable_fab)).perform(click())

        onView(withId(R.id.date_input)).perform(replaceText("Date"))
        onView(withId(R.id.morning_input)).perform(replaceText("Morning"))
        onView(withId(R.id.lunch_input)).perform(replaceText("Lunch"))
        onView(withId(R.id.dinner_input)).perform(replaceText("Dinner"))
        onView(withId(R.id.morning_kcal_input)).perform(replaceText("Kcal"))
        onView(withId(R.id.lunch_kcal_input)).perform(replaceText("Kcal"))
        onView(withId(R.id.dinner_kcal_input)).perform(replaceText("Kcal"))
        onView(withId(R.id.save_btn)).perform(click())
    }

    @Test
    fun new_forageable_is_displayed_in_list() {
        onView(withText("Name")).check(matches(isDisplayed()))
        onView(withText("Address")).check(matches(isDisplayed()))
    }

    @Test
    fun new_forageable_is_displayed_in_detail() {
        onView(withText("Name")).perform(click())
        onView(withText("Name")).check(matches(isDisplayed()))
        onView(withText("Address")).check(matches(isDisplayed()))
        onView(withText(("Currently out of season"))).check(matches(isDisplayed()))
        onView(withText("Notes")).check(matches(isDisplayed()))
    }

    @Test
    fun edit_new_forageable() {
        onView(withText("Name")).perform(click())
        onView(withId(R.id.edit_forageable_fab)).perform(click())
        onView(withId(R.id.date_input)).perform(replaceText("New Date"))
        onView(withId(R.id.morning_input)).perform(replaceText("New Morning"))
        onView(withId(R.id.lunch_input)).perform(replaceText("New Lunch"))
        onView(withId(R.id.dinner_input)).perform(replaceText("New Dinner"))
        onView(withId(R.id.morning_kcal_input)).perform(replaceText("New Kcal"))
        onView(withId(R.id.lunch_kcal_input)).perform(replaceText("New Kcal"))
        onView(withId(R.id.dinner_kcal_input)).perform(replaceText("New Kcal"))
        //onView(withId(R.id.in_season_checkbox)).perform(click())
        onView(withId(R.id.save_btn)).perform(click())
        Thread.sleep(1000)
        onView(withText("New Name")).perform(click())
        Thread.sleep(2000)
        onView(withText("New Name")).check(matches(isDisplayed()))
        onView(withText("New Address")).check(matches(isDisplayed()))
        onView(withText(("Currently in season"))).check(matches(isDisplayed()))
        onView(withText("New Notes")).check(matches(isDisplayed()))
    }

    @Test
    fun delete_new_forageable() {
        onView(withText("Name")).perform(click())
        onView(withId(R.id.edit_forageable_fab)).perform(click())
        onView(withId(R.id.delete_btn)).perform(click())
        Thread.sleep(1000)
        onView(withText("Name")).check(doesNotExist())
    }
}
