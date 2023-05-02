package com.example.tsdc_vinilos_equipo6.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.tsdc_vinilos_equipo6.R
import com.example.tsdc_vinilos_equipo6.ui.MainActivity
import org.hamcrest.Matchers.not
import org.hamcrest.core.AllOf.allOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectorListTest {

    @Rule
    @JvmField
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)


    fun btnMenuUsuarioClick() {
        // click en Menu Usuario
        onView(withId(R.id.MenuUserButton))
            .perform(click())
    }

    fun btnCollectorCatalog(){
        // click en Menu Usuario
        onView(withId(R.id.ColecctionUserButton)).perform(click())
    }

    @Test
    fun AlbumListTest() {
        // click en Menu Usuario
        btnMenuUsuarioClick()

        //Click Catalogo de Artistas
        btnCollectorCatalog()

        onView(withId(R.id.AlbumTitle)).check(matches(withText("Listado de Coleccionistas")))
        onView(allOf(withId(R.id.AlbumName), not(withText(""))))
        onView(allOf(withId(R.id.AlbumDescription), not(withText(""))))
    }

}