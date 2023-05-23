package com.example.tsdc_vinilos_equipo6.test

import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.tsdc_vinilos_equipo6.R
import com.example.tsdc_vinilos_equipo6.ui.MainActivity
import com.example.tsdc_vinilos_equipo6.utils.CustomAssertions
import com.example.tsdc_vinilos_equipo6.utils.CustomAssertions.Companion.greaterItem
import org.hamcrest.CoreMatchers.not
import org.hamcrest.core.AllOf.allOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class AlbumDetailTest {

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
        onView(allOf(withId(idView), ViewMatchers.isDisplayed()))
        //Validamos que no venga vacio algun TextView de tipo idView
        onView(allOf(withId(idView), not(withText(""))))
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        onView(allOf(withId(idView), withText(valueToSearch)))
    }

    fun validateTextViewNoExistent(idView: Int, valueToSearch: String) {
        //Validamos que no es mostrado algun TextView de tipo idView
        onView(allOf(withId(idView), not(ViewMatchers.isDisplayed())))
        //Validamos que no si existe un TextView de tipo idView con el texto valueToSearch
        onView(allOf(withId(idView), not(withText(valueToSearch))))
    }

    fun getTextViewByValue(idView: Int, valueToSearch: String): ViewInteraction? {
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        return onView(allOf(withId(idView), withText(valueToSearch)))
    }

    fun navegateToListAlbums() {
        //Click en Menu Usuario
        clickIntoButtonById(R.id.MenuUserButton)
        //Click Catalogo de Albumes
        clickIntoButtonById(R.id.AlbumUserButton)
    }

    @Test
    fun UseAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.tsdc_vinilos_equipo6", appContext.packageName)
    }

    @Test
    fun CheckTracksListTest() {
        /*
            Prueba que tiene como objetivo verificar que exista un numero minimo de canciones de asociados a un album
         */
        //Constantes que se pueden modificar con base a los criterios deseados
        val albumNameToSearch = "Meteora"
        val tracksMinimum = 2

        //Navegamos al view de ListAlbums
        navegateToListAlbums()

        //Agregamos un tiempo de espera
        SystemClock.sleep(delayService)

        //Damos click en textView con el AlbumName = albumNameToSearch
        clickIntoButtonByText(R.id.AlbumName, albumNameToSearch)

        //Agregamos un tiempo de espera
        SystemClock.sleep(delayService)

        //Validamos que el listado tenga un minimo de premios
        onView(withId(R.id.album_tracks_rv)).check(
            greaterItem(
                tracksMinimum
            )
        )
    }

    @Test
    fun ConsultExistentAlbumDetailTest() {/*
            Prueba que tiene como objetivo verificar la informaci�n del detalle de un album existente
        */

        //Constantes que se pueden modificar con base a los criterios deseados
        val albumNameToSearch = "Meteora"
        val albumReleaseDateToSearch = "2009-08-25"
        val albumDescriptionToSearch =
            "Segundo álbum del grupo Linkin Park, lanzado el 25 de marzo de 2003. El álbum ha vendido 13 300 000 de copias en todo el mundo, 6 millones solo en los EE. UU. Solo en su primera semana vendió un estimado de 810 000 copias. Meteora es un disco cargado de una estética plenamente callejera, con grandes influencias del grafiti y con el estilo único que les caracteriza. El nombre del mismo estuvo inspirado en la región rocosa de Meteora en Grecia, donde están construidos numerosos monasterios encima de las piedras."
        val albumGenreToSearch = "Rock"
        val albumRecordLabelToSearch = "Sony Music"
        val albumPerformerToSearch = "Chester Bennington"
        val albumTrackToSearch = "Somewhere I Belong"
        val albumTrackTimeToSearch = "3:33"
        val albumTracksMin = 3

        //Navegamos al view de ListAlbums
        navegateToListAlbums()

        //Agregamos un tiempo de espera
        SystemClock.sleep(delayService)

        //Validamos si el fragment_album es mostrado
        onView(withId(R.id.fragment_album)).check(matches(ViewMatchers.isDisplayed()))

        //Damos click en textView con el AlbumName = albumNameToSearch
        clickIntoButtonByText(R.id.AlbumName, albumNameToSearch)

        //Agregamos un tiempo de espera
        SystemClock.sleep(delayService)

        //Validamos que exista el textView AlbumName y coincida con el album buscado
        validateTextView(R.id.albumName, albumNameToSearch)

        //Validamos si el es visible el elemento AlbumCover
        onView(allOf(withId(R.id.albumImage), ViewMatchers.isDisplayed()))

        //Validamos si el es visible el elemento albumTitleReleased
        onView(allOf(withId(R.id.albumTitleReleased), ViewMatchers.isDisplayed()))

        //Validamos que exista el textView AlbumReleaseData y coincida con la fecha de lanzamiento buscado
        validateTextView(R.id.albumReleasedDate, albumReleaseDateToSearch)

        //Validamos si el es visible el elemento albumTitleDescription
        onView(allOf(withId(R.id.albumTitleDescription), ViewMatchers.isDisplayed()))

        //Validamos que exista el textView AlbumDescription y coincida con la descripcion del album buscada
        validateTextView(R.id.albumDescription, albumDescriptionToSearch)

        //Validamos si el es visible el elemento albumTitleGenre
        onView(allOf(withId(R.id.albumTitleGenre), ViewMatchers.isDisplayed()))

        //Validamos que exista el textView AlbumGenre y coincida con la genero del album buscado
        validateTextView(R.id.albumGenre, albumGenreToSearch)

        //Validamos si el es visible el elemento albumTitleRecordLabel
        onView(allOf(withId(R.id.albumTitleRecordLabel), ViewMatchers.isDisplayed()))

        //Validamos que exista el textView Artist y coincida con el artista del album buscado
        validateTextView(R.id.albumRecordLabel, albumRecordLabelToSearch)

        //Validamos si el es visible el elemento albumTitleArtist
        onView(allOf(withId(R.id.albumTitleArtist), ViewMatchers.isDisplayed()))

        //Validamos que exista el textView albumArtist y coincida con el artista del album buscado
        validateTextView(R.id.albumArtist, albumPerformerToSearch)

        //Validamos que exista el textView TrackName y coincida con el canción del album buscada
        validateTextView(R.id.TrackName, albumTrackToSearch)

        //Validamos que exista el textView TrackTime y coincida con el tiempo de la canción buscada
        validateTextView(R.id.TrackTime, albumTrackTimeToSearch)

        //Validamos que el listado de canciones tenga un minimo de canciones
        onView(withId(R.id.album_tracks_rv)).check(greaterItem(albumTracksMin))

        //Damos click en back
        Espresso.pressBack()

        //Validamos que al darle volver nos lleve nuevamente al listado de Artistas
        onView(withId(R.id.fragment_album)).check(matches(ViewMatchers.isDisplayed()))

        //Damos click en back
        Espresso.pressBack()

        //Validamos que al darle volver nos lleve nuevamente a la ventana de Usuario
        onView(withId(R.id.fragment_user)).check(matches(ViewMatchers.isDisplayed()))
    }
}