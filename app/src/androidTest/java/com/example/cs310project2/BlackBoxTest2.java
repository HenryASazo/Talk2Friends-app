package com.example.cs310project2;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
        //import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Pattern;

public class BlackBoxTest2 {
//    @Rule
//    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testEmailInput() {
        String emailInput = "hsazo@usc.edu";
        Espresso.onView(ViewMatchers.withId(R.id.si_email_input))
                .perform(ViewActions.typeText(emailInput))
                .perform(ViewActions.closeSoftKeyboard()); // Close the soft keyboard after typing

        // Add any additional assertions or actions you want to perform after entering text
    }

    @Test
    public void testPasswordInput() {
        String emailInput = "1234";
        Espresso.onView(ViewMatchers.withId(R.id.si_password_input))
                .perform(ViewActions.typeText(emailInput))
                .perform(ViewActions.closeSoftKeyboard()); // Close the soft keyboard after typing

        // Add any additional assertions or actions you want to perform after entering text
    }
    @Test
    public void testButtonsSigninSignup() {
        // Button 1 Sign
        Espresso.onView(ViewMatchers.withId(R.id.signin))
                .perform(ViewActions.click());

        // Button 2 Sign up
        Espresso.onView(ViewMatchers.withId(R.id.signup_btn))
                .perform(ViewActions.click());

    }

    @Test
    public void testUpdateProfile() {
        // EditText 1
        String text1 = "UpdatePassword";
        Espresso.onView(ViewMatchers.withId(R.id.password_input))
                .perform(ViewActions.typeText(text1))
                .perform(ViewActions.closeSoftKeyboard());

        // EditText 2
        String text2 = "23";
        Espresso.onView(ViewMatchers.withId(R.id.age_input))
                .perform(ViewActions.typeText(text2))
                .perform(ViewActions.closeSoftKeyboard());

        // EditText 3
        String text3 = "UpdateNative";
        Espresso.onView(ViewMatchers.withId(R.id.status_input))
                .perform(ViewActions.typeText(text3))
                .perform(ViewActions.closeSoftKeyboard());

        // CheckBox 1
        Espresso.onView(ViewMatchers.withId(R.id.reading))
                .perform(ViewActions.click());

        // CheckBox 2
        Espresso.onView(ViewMatchers.withId(R.id.music))
                .perform(ViewActions.click());

        // CheckBox 3
        Espresso.onView(ViewMatchers.withId(R.id.sports))
                .perform(ViewActions.click());

        // Submit Button
        Espresso.onView(ViewMatchers.withId(R.id.submit_btn))
                .perform(ViewActions.click());
    }

    @Test
    public void testSignUpEmailValid() {
        // Replace R.id.emailEditText and R.id.validateButton with the actual IDs of your EditText and button

        // Valid email
        String validEmail = "validEmail@usc.edu";
        Espresso.onView(ViewMatchers.withId(R.id.email_input))
                .perform(ViewActions.typeText(validEmail))
                .perform(ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.sign_up_submit_btn))
                .perform(ViewActions.click());

        // Add assertions or additional actions after clicking the validate button for a valid email

        // Invalid email
        String invalidEmail = "invalidEmail-@gmail.com";
        Espresso.onView(ViewMatchers.withId(R.id.email_input))
                .perform(ViewActions.clearText())
                .perform(ViewActions.typeText(invalidEmail))
                .perform(ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.sign_up_submit_btn))
                .perform(ViewActions.click());

    }

    // Utility method to check if an email is valid
    private boolean isValidEmail(String email) {
        // Use a regular expression for basic email validation
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
        return pattern.matcher(email).matches();
    }
}
