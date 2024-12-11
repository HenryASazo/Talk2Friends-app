package com.example.cs310project2;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BlackBoxTest3 {

    public static final String USERNAME = "caddei";
    public static final String TOPIC = "Test Meeting";
    public static final String TIME = "12:00";
    public static final String LOCATION = "THH 201";
    public static final String MEETING = "TEST  ";
    @Test
    public void testAddNewMeeting() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), meetings.class);
        intent.putExtra("user", USERNAME);

        ActivityScenario.launch(intent);
        onView(withId(R.id.topic_input)).perform(typeText(TOPIC), closeSoftKeyboard());
        onView(withId(R.id.time_input)).perform(typeText(TIME), closeSoftKeyboard());
        onView(withId(R.id.location_input)).perform(typeText(LOCATION), closeSoftKeyboard());
        onView(withId(R.id.create_btn)).perform(click());
    }
    @Test
    public void testReserveSpot() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), meetings.class);
        intent.putExtra("user", USERNAME);
        ActivityScenario<friends> activityScenario = ActivityScenario.launch(intent);
        int initialSpots = getCountOfButtonsWithText(R.id.meetings, "Reserve Spot");

        onView(allOf(withText("Add"), withTagValue(is((Object) MEETING))))
                .perform(click());

        int updatedSpots = getCountOfButtonsWithText(R.id.meetings, "Reserve Spot");
        assertThat(updatedSpots, is(initialSpots - 1));
    }
    @Test
    public void testCancelSpot() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), meetings.class);
        intent.putExtra("user", USERNAME);
        ActivityScenario<friends> activityScenario = ActivityScenario.launch(intent);
        int initialSpots = getCountOfButtonsWithText(R.id.meetings, "Cancel Spot");

        onView(allOf(withText("Remove"), withTagValue(is((Object) MEETING))))
                .perform(click());

        int updatedSpots = getCountOfButtonsWithText(R.id.meetings, "Cancel Spot");
        assertThat(updatedSpots, is(initialSpots - 1));
    }
    @Test
    public void testNavigationToProfileFromHome() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), friends.class);
        intent.putExtra("user", USERNAME);
        ActivityScenario<friends> activityScenario = ActivityScenario.launch(intent);
        onView(withId(R.id.profile_btn)).perform(click());
        onView(withId(R.id.profile_unique)).check(matches(isDisplayed()));
    }
    @Test
    public void testNavigationToFriendsFromHome() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), friends.class);
        intent.putExtra("user", USERNAME);
        ActivityScenario<friends> activityScenario = ActivityScenario.launch(intent);
        onView(withId(R.id.friends_btn)).perform(click());
        onView(withId(R.id.friends_unique)).check(matches(isDisplayed()));
    }
    private int getCountOfButtonsWithText(int linearLayoutId, String buttonText) {
        final int[] count = {0};
        onView(withId(linearLayoutId)).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (noViewFoundException != null) {
                    throw noViewFoundException;
                }
                LinearLayout linearLayout = (LinearLayout) view;
                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                    View child = linearLayout.getChildAt(i);
                    if (child instanceof Button && ((Button) child).getText().equals(buttonText)) {
                        count[0]++;
                    }
                }
            }
        });
        return count[0];
    }
}
