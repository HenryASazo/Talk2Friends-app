package com.example.cs310project2;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CountDownLatch;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class BlackBoxTest1 {

    public static final String USERNAME = "cksubram";
    public static final String FRIEND_TO_ADD = "hsazo";
    public static final String FRIEND_TO_REMOVE = "caddei";

    public static final String EMAIL_ADDRESS = "test";

    @Test
    public void testAddingFriendUpdatesFriendList() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), friends.class);
        intent.putExtra("user", USERNAME);
        ActivityScenario<friends> activityScenario = ActivityScenario.launch(intent);
        // Check the initial state of the friend list
        int initialFriendCount = getCountOfButtonsWithText(R.id.friends, "Add");

        // Add a new friend
        Espresso.onView(allOf(withText("Add"), withTagValue(is((Object) FRIEND_TO_ADD))))
                .perform(click());

        // Wait for a short period to ensure the friend list is updated
        try {
            Thread.sleep(1000); // 1 second sleep; adjust as necessary
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if the friend list is updated
        int updatedFriendCount = getCountOfButtonsWithText(R.id.friends, "Add");
        assertThat(updatedFriendCount, is(initialFriendCount - 1));

        //remove friend to return to old state
        Espresso.onView(allOf(withText("Remove"), withTagValue(is((Object) FRIEND_TO_ADD))))
                .perform(click());
    }

    @Test
    public void testRemovingFriendUpdatesFriendList() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), friends.class);
        intent.putExtra("user", USERNAME);
        ActivityScenario<friends> activityScenario = ActivityScenario.launch(intent);
        // Check the initial state of the friend list
        int initialFriendCount = getCountOfButtonsWithText(R.id.friends, "Remove");

        // Add a new friend
        Espresso.onView(allOf(withText("Remove"), withTagValue(is((Object) FRIEND_TO_REMOVE))))
                .perform(click());

        // Wait for a short period to ensure the friend list is updated
        try {
            Thread.sleep(1000); // 1 second sleep; adjust as necessary
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if the friend list is updated
        int updatedFriendCount = getCountOfButtonsWithText(R.id.friends, "Remove");
        assertThat(updatedFriendCount, is(initialFriendCount - 1));

        //add friend to return to old state
        Espresso.onView(allOf(withText("Add"), withTagValue(is((Object) FRIEND_TO_REMOVE))))
                .perform(click());
    }

    @Test
    public void testNavigationToHomeFromFriends() {
        // Launch the friends activity
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), friends.class);
        intent.putExtra("user", USERNAME);
        ActivityScenario<friends> activityScenario = ActivityScenario.launch(intent);

        // Perform a click on the meetings button
        Espresso.onView(withId(R.id.meetings_btn)).perform(click());

        // Check for a view that is unique to the profile activity
        Espresso.onView(withId(R.id.home_unique)).check(matches(isDisplayed()));
    }


    @Test
    public void testNavigationToProfileFromFriends() {
        // Launch the friends activity
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), friends.class);
        intent.putExtra("user", USERNAME);
        ActivityScenario<friends> activityScenario = ActivityScenario.launch(intent);

        // Perform a click on the meetings button
        Espresso.onView(withId(R.id.profile_btn)).perform(click());

        // Check for a view that is unique to the profile activity
        Espresso.onView(withId(R.id.profile_unique)).check(matches(isDisplayed()));
    }


    @Test
    public void testSendEmail() throws InterruptedException {
        // Launch the email activity
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), email.class);
        intent.putExtra("user", USERNAME);
        ActivityScenario<email> activityScenario = ActivityScenario.launch(intent);

        // Type in the email address
        Espresso.onView(withId(R.id.email_input)).perform(typeText(EMAIL_ADDRESS), closeSoftKeyboard());

        // Click the send button
        Espresso.onView(withId(R.id.send_btn)).perform(click());

        // Verify that an email intent is triggered
        //intended(hasAction(Intent.ACTION_SENDTO));

        //verify that the user has been added to the db
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference reference = root.getReference("users/" + EMAIL_ADDRESS);
        CountDownLatch latch = new CountDownLatch(1);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User currUser = snapshot.getValue(User.class);
                latch.countDown(); // Decrement the latch count
                assertEquals(EMAIL_ADDRESS, currUser.getEmail());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                fail("Firebase error: " + error.getMessage());
                latch.countDown();
            }
        });

        latch.await();

    }

    // Utility method to count items in a LinearLayout
    // Utility method to count buttons with a specific text in a LinearLayout
    private int getCountOfButtonsWithText(int linearLayoutId, String buttonText) {
        final int[] count = {0};
        Espresso.onView(withId(linearLayoutId)).check(new ViewAssertion() {
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