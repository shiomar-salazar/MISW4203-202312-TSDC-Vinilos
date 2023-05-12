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
import org.hamcrest.Matchers.not
import org.hamcrest.core.AllOf.allOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AlbumListTest {

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
    fun CheckAlbumsListTest() {
        /*
            Prueba que tiene como objetivo verificar que exista un numero minimo de albumes
         */
        //Constantes que se pueden modificar con base a los criterios deseados
        val albumsMinimum = 2

        //Navegamos al view de ListAlbums
        navegateToListAlbums()

        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)

        //Validamos que el listado tenga un minimo de albumes
        onView(withId(R.id.fragment_album)).check(
            CustomAssertions.greaterItem(
                albumsMinimum
            )
        )
    }

    @Test
    fun ConsultExistentAlbumTest() {
        /*
            Prueba que tiene como objetivo verificar la informaci�n de un �lbum existente
        */

        //Constantes que se pueden modificar con base a los criterios deseados
        val titleToSearch = "Listado de Albumes"
        val albumNameToSearch = "Meteora"
        val albumPerformerToSearch = "Chester Bennington"


        //Navegamos al view de ListAlbums
        navegateToListAlbums()

        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)

        //Validamos si el fragment_album es mostrado
        onView(withId(R.id.fragment_album)).check(matches(ViewMatchers.isDisplayed()))

        //Validamos el textView Title exista
        validateTextView(R.id.AlbumTitle, titleToSearch)

        //Validamos el textView AlbumName coincida con el album buscado
        validateTextView(R.id.AlbumName, albumNameToSearch)

        //Validamos el textView AlbumPerformer coincida con el performer buscado
        validateTextView(R.id.AlbumPerformer, albumPerformerToSearch)

        //Damos click en textView con el AlbumName = albumNameToSearch
        clickIntoButtonByText(R.id.AlbumName, albumNameToSearch)

        //Damos click en back
        Espresso.pressBack()

        //Validamos que al darle volver nos lleve nuevamente al listado de Album
        onView(withId(R.id.fragment_album)).check(matches(ViewMatchers.isDisplayed()))

        //Damos click en back
        Espresso.pressBack()

        //Validamos que al darle volver nos lleve nuevamente a la ventana de Usuario
        onView(withId(R.id.fragment_user)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun ConsultNonExistentAlbumTest() {
        /*
            Prueba que tiene como objetivo verificar que no existe la informaci�n de un album especifico en el listado de albumes
        */

        //Constantes que se pueden modificar con base a los criterios deseados
        val titleToSearch = "Listado de Albumes"
        val albumNameToSearch = "Prueba"
        val albumPerformerToSearch = "Paquita la del barrio"

        //Navegamos al view de ListAlbums
        navegateToListAlbums()

        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)

        //Validamos si el fragment_album es mostrado
        onView(withId(R.id.fragment_album)).check(matches(ViewMatchers.isDisplayed()))

        //Validamos el textView Title exista
        validateTextView(R.id.AlbumTitle, titleToSearch)

        //Validamos el textView AlbumName no tenga coincidencias
        validateTextViewNoExistent(R.id.AlbumName, albumNameToSearch)

        //Validamos el textView AlbumPerformer no tenga coincidencias
        validateTextViewNoExistent(R.id.AlbumPerformer, albumPerformerToSearch)

        //Damos click en back
        Espresso.pressBack()

        //Validamos que al darle volver nos lleve nuevamente a la ventana de Usuario
        onView(withId(R.id.fragment_user)).check(matches(ViewMatchers.isDisplayed()))
    }

}
