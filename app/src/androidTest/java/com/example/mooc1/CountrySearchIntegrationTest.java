package com.example.mooc1;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class CountrySearchIntegrationTest {

    @Rule
    public ActivityTestRule<CountrySearch> activityRule = new ActivityTestRule<>(CountrySearch.class);

    @Test
    public void testFetchButtonClicked() {
        // Simulate user interaction: click the fetch button
        Espresso.onView(withId(R.id.button_fetch)).perform(ViewActions.click());

        // Check if the progress bar is visible
        Espresso.onView(withId(R.id.progress_bar)).check(ViewAssertions.matches(isDisplayed()));

        // Wait for the request to complete (you might need to add a delay if necessary)

        // Check if the result text view is visible and contains the expected text
        Espresso.onView(withId(R.id.text_view_result))
                .check(ViewAssertions.matches(Matchers.allOf(isDisplayed(), withText("Country not found"))));
    }
}
