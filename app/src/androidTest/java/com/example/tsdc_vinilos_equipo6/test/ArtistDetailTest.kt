package com.example.tsdc_vinilos_equipo6.test

import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
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
import com.example.tsdc_vinilos_equipo6.utils.CustomAssertions
import org.hamcrest.Matchers.not
import org.hamcrest.core.AllOf.allOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class ArtistDetailTest {

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
    fun CheckAlbumsListTest() {
        /*
            Prueba que tiene como objetivo verificar que exista un numero minimo de albumes de asociados a un artista
         */
        //Constantes que se pueden modificar con base a los criterios deseados
        val artistNameToSearch = "Chester Bennington"
        val albumsMinimum = 2

        //Navegamos al view de ListArtists
        navegateToListArtists()

        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)

        //Damos click en textView con el ArtistName = artistNameToSearch
        clickIntoButtonByText(R.id.ArtistName, artistNameToSearch)

        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)

        //Validamos que el listado tenga un minimo de albumes
        onView(withId(R.id.artist_albums_rv)).check(
            CustomAssertions.greaterItem(
                albumsMinimum
            )
        )
    }

    @Test
    fun CheckPrizesListTest() {
        /*
            Prueba que tiene como objetivo verificar que exista un numero minimo de premios de asociados a un artista
         */
        //Constantes que se pueden modificar con base a los criterios deseados
        val artistNameToSearch = "Chester Bennington"
        val prizesMinimum = 2

        //Navegamos al view de ListArtists
        navegateToListArtists()

        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)

        //Damos click en textView con el ArtistName = artistNameToSearch
        clickIntoButtonByText(R.id.ArtistName, artistNameToSearch)

        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)

        //Validamos que el listado tenga un minimo de premios
        onView(withId(R.id.artist_prizes_rv)).check(
            CustomAssertions.greaterItem(
                prizesMinimum
            )
        )
    }

    @Test
    fun ConsultExistentArtistDetailTest() {
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

        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)

        //Validamos si el fragment_artist es mostrado
        onView(withId(R.id.artist_detail_rv)).check(matches(isDisplayed()))

        //Validamos el textView Name exista
        validateTextView(R.id.ArtistName, titleToSearch)

        //Validamos si la biografia es mostrado
        onView(withId(R.id.BiographyDescription)).check(matches(isDisplayed()))

        //Validamos el textView ArtistName coincida con el artista buscado
        validateTextView(R.id.albums_title_tv, "Albumes Principales")

        //Validamos el textView Premios coincida con el artista buscado
        validateTextView(R.id.prizes_title_tv, "Premios")

        //Validamos si La images es desplegada es mostrado
        onView(withId(R.id.artistImage)).check(matches(isDisplayed()))


        //Validamos si el listado de Albumes es mostrado
        validateTextView(R.id.artist_albums_rv, "Meteora")


        //Validamos si el listado de Premios es mostrado
        validateTextView(R.id.artist_prizes_rv, "Premio Grammy")

        //Damos click en back
        Espresso.pressBack()

        //Validamos que al darle volver nos lleve nuevamente al listado de Artistas
        onView(withId(R.id.fragment_artist)).check(matches(isDisplayed()))

        //Damos click en back
        Espresso.pressBack()

        //Validamos que al darle volver nos lleve nuevamente a la ventana de Usuario
        onView(withId(R.id.fragment_user)).check(matches(isDisplayed()))
    }
}