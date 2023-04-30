package com.example.tsdc_vinilos_equipo6

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.tsdc_vinilos_equipo6.ui.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Rule
    var mActivityTestRule = ActivityScenarioRule(
        MainActivity::class.java
    )
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.tsdc_vinilos_equipo6", appContext.packageName)
    }


    fun btnMenuUsuarioClick(){
        // click en Menu Usuario
        onView(withId(R.id.MenuUserButton)).perform(click())
    }
    fun btnArtistCatalog(){
        // click en Menu Usuario
        onView(withId(R.id.ArtistUserButton)).perform(click())
    }
    fun btnAlbumCatalog(){
        // click en Menu Usuario
        onView(withId(R.id.AlbumUserButton)).perform(click())
    }
    fun btnCollectorCatalog(){
        // click en Menu Usuario
        onView(withId(R.id.ColecctionUserButton)).perform(click())
    }
    @Test
    fun ArtistListTest(){
        // click en Menu Usuario
        btnMenuUsuarioClick()

        //Click Catalogo de Artistas
        btnArtistCatalog()
    }


    @Test
    fun AlbumCatalogTest(){
        // click en Menu Usuario
        btnMenuUsuarioClick()

        //Click Catalogo de Artistas
        btnAlbumCatalog()
    }

    @Test
    fun CollectorCatalogTest(){
        // click en Menu Usuario
        btnMenuUsuarioClick()

        //Click Catalogo de Artistas
        btnCollectorCatalog()
    }
}