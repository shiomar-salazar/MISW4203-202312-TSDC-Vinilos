package com.example.tsdc_vinilos_equipo6.test

import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.tsdc_vinilos_equipo6.R
import com.example.tsdc_vinilos_equipo6.ui.MainActivity
import org.hamcrest.Matchers.not
import org.hamcrest.core.AllOf.allOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class ArtisListTest {

    @Rule
    @JvmField
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    //Constante que define el tiempo de espera para que se carguen los datos retornados por el adapter
    val delayService = Integer.toUnsignedLong(5000)

    fun clickIntoButtonById(idView: Int) {
        //Damos click en el boton idView
        onView(withId(idView)).perform(click())
    }

    fun clickIntoButtonByText(idView: Int, valueToSearch: String) {
        //Damos click en el boton idView
        getTextViewByValue(idView, valueToSearch)?.perform(click())
    }

    fun validateTextView(idView: Int, valueToSearch: String) {
        //Validamos si es mostrado algun TextView de tipo idView
        onView(allOf(withId(idView), isDisplayed()))
        //Validamos que no venga vacio algun TextView de tipo idView
        onView(allOf(withId(idView), not(withText(""))))
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        onView(allOf(withId(idView), withText(valueToSearch)))
    }

    fun validateTextViewNoExistent(idView: Int, valueToSearch: String) {
        //Validamos que no es mostrado algun TextView de tipo idView
        onView(allOf(withId(idView), not(isDisplayed())))
        //Validamos que no si existe un TextView de tipo idView con el texto valueToSearch
        onView(allOf(withId(idView), not(withText(valueToSearch)) ))
    }

    fun getTextViewByValue(idView: Int, valueToSearch: String): ViewInteraction? {
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        return onView(allOf(withId(idView), withText(valueToSearch)))
    }

    fun navegateToListArtists() {
        //Click en Menu Usuario
        clickIntoButtonById(R.id.MenuUserButton)
        //Click Catalogo de Artistas
        clickIntoButtonById(R.id.ArtistUserButton)
    }

    @Test
    fun UseAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.tsdc_vinilos_equipo6", appContext.packageName)
    }

    @Test
    fun ConsultExistentArtistTest() {
        /*
            Prueba que tiene como objetivo verificar la informaci�n de un artista existente
         */
        //Constantes que se pueden modificar con base a los criterios deseados
        val titleToSearch = "Listado de Artistas"
        val artistNameToSearch = "Chester Bennington"
        val artistDateToSearch = "1976-03-20"

        //Navegamos al view de ListArtists
        navegateToListArtists()

        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)

        //Validamos si el fragment_artist es mostrado
        onView(withId(R.id.fragment_artist)).check(matches(isDisplayed()))

        //Validamos el textView Title exista
        validateTextView(R.id.ArtistTitle, titleToSearch)

        //Validamos el textView ArtistName coincida con el artista buscado
        validateTextView(R.id.ArtistName, artistNameToSearch)

        //Validamos el textView ArtistName coincida con el artista buscado
        validateTextView(R.id.ArtisDate, artistDateToSearch)

        //Damos click en textView con el ArtistName = artistNameToSearch
        clickIntoButtonByText(R.id.ArtistName, artistNameToSearch)

        //Damos click en back
        pressBack()

        //Validamos que al darle volver nos lleve nuevamente a la ventana de Usuario
        onView(withId(R.id.fragment_user)).check(matches(isDisplayed()))
    }

    @Test
    fun ConsultNonExistentArtistTest() {
        /*
            Prueba que tiene como objetivo verificar que no existe la informaci�n de un artista especifico en el listado de artistas
         */

        //Constantes que se pueden modificar con base a los criterios deseados
        val titleToSearch = "Listado de Artistas"
        val artistNameToSearch = "Prueba"
        val artistDateToSearch = "2023-03-20"

        //Navegamos al view de ListArtists
        navegateToListArtists()

        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)

        //Validamos si el fragment_artist es mostrado
        onView(withId(R.id.fragment_artist)).check(matches(isDisplayed()))

        //Validamos el textView Title exista
        validateTextView(R.id.ArtistTitle, titleToSearch)

        //Validamos el textView ArtistName no tenga coincidencias
        validateTextViewNoExistent(R.id.ArtistName, artistNameToSearch)

        //Validamos el textView ArtistName no tenga coincidencias
        validateTextViewNoExistent(R.id.ArtisDate, artistDateToSearch)

        //Damos click en back
        pressBack()

        //Validamos que al darle volver nos lleve nuevamente a la ventana de Usuario
        onView(withId(R.id.fragment_user)).check(matches(isDisplayed()))
    }

}