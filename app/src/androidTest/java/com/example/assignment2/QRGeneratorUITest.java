package com.example.assignment2;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.assignment2.MainActivity;
import com.example.assignment2.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
public class QRGeneratorUITest {

    private String textToType;

    // Launch the activity under test before each test method and
    // before any method annotated with @Before
    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initValidString(){
        textToType = "https://www.facebook.com";   // EUR20.0
        onView(withId(R.id.btnGenerateQRCode)).perform(click());
    }

    @Test
    public void genQR_Test(){
        onView(withId(R.id.editText))
                .perform(replaceText(""))
                .perform(typeText(textToType), closeSoftKeyboard());

        onView(withId(R.id.btnGenerate)).perform(click());

        onView(withId(R.id.btnSave)).perform(click());
    }


}
