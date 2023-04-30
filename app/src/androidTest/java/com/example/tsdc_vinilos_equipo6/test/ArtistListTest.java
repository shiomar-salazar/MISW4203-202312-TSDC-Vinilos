package com.example.tsdc_vinilos_equipo6.test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.AllOf.allOf;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.tsdc_vinilos_equipo6.R;
import com.example.tsdc_vinilos_equipo6.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kotlin.jvm.JvmField;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ArtistListTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);
    //public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /*@Before
    public void launchActivity() {
        ActivityScenario.launch(MainActivity.class);
    }**/

    @Test
    public void mainActivityTest2() {
        ViewInteraction menuUserBtn = onView(allOf(withId(R.id.MenuUserButton), withText("Menu Usuario"), isDisplayed()));
        menuUserBtn.perform(click());
        //onView(withId(R.id.MenuUserButton)).perform(click());
    }
}
