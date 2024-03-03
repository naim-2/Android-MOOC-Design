// This is an interface test

package com.example.mooc1;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class HomePageInterfaceTest {

    @Rule
    public ActivityScenarioRule<HomePage> activityScenarioRule = new ActivityScenarioRule<>(HomePage.class);

    @Test
    public void testLaunchMainActivity() {
        // Check if the main activity button is displayed
        Espresso.onView(withId(R.id.button_main_activity))
                .check(matches(isDisplayed()));

        // Perform click on the main activity button
        Espresso.onView(withId(R.id.button_main_activity))
                .perform(ViewActions.click());

        // Verify that the main activity is launched
        Espresso.onView(withId(R.id.button_main_activity))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testLaunchMainActivity2() {
        // Check if the main activity 2 button is displayed
        Espresso.onView(withId(R.id.button_main_activity_2))
                .check(matches(isDisplayed()));

        // Perform click on the main activity 2 button
        Espresso.onView(withId(R.id.button_main_activity_2))
                .perform(ViewActions.click());

        // Verify that the main activity 2 is launched
        Espresso.onView(withId(R.id.button_main_activity_2))
                .check(matches(isDisplayed()));
    }
}


