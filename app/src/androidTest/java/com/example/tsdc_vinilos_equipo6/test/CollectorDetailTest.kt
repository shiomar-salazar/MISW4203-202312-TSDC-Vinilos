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
class CollectorDetailTest {

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
        onView(allOf(withId(idView), not(withText(valueToSearch))))
    }

    fun getTextViewByValue(idView: Int, valueToSearch: String): ViewInteraction? {
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        return onView(allOf(withId(idView), withText(valueToSearch)))
    }

    fun navegateToListCollectors() {
        //Click en Menu Usuario
        clickIntoButtonById(R.id.MenuUserButton)
        //Click Catalogo de Artistas
        clickIntoButtonById(R.id.ColecctionUserButton)
    }

    @Test
    fun UseAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.tsdc_vinilos_equipo6", appContext.packageName)
    }

    @Test
    fun CheckCollectorListTest() {
        /*
            Prueba que tiene como objetivo verificar que exista un numero minimo de albumes de asociados a un collecionista
         */
        //Constantes que se pueden modificar con base a los criterios deseados
        val collectorName = "Haiber H. Galindo"
        val albumsMinimum = 1

        //Navegamos al view de Colleector List
        navegateToListCollectors()

        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)

        //Damos click en textView con el ArtistName = artistNameToSearch
        clickIntoButtonByText(R.id.collectorName, collectorName)

        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)

        //Validamos que el listado tenga un minimo de albumes
        onView(withId(R.id.albums_rv)).check(
            CustomAssertions.greaterItem(
                albumsMinimum
            )
        )
    }

    @Test
    fun ConsultExistentCollectorDetailTest() {
        /*
            Prueba que tiene como objetivo verificar la informaci�n de un coleccionista existente
         */
        //Constantes que se pueden modificar con base a los criterios deseados
        val titleToSearch = "Listado de Coleccionistas"
        val collectorName = "Haiber H. Galindo"
        val collectorEmailToSearch = "hgalindo619@gmail.com"
        val collectorPhoneToSearch = "3102178976"

        //Navegamos al view de ListArtists
        navegateToListCollectors()

        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)

        //Validamos si el fragment_artist es mostrado
        onView(withId(R.id.fragment_collector)).check(matches(isDisplayed()))

        //Validamos el textView Title exista
        validateTextView(R.id.collectorTitle, titleToSearch)

        //Validamos el textView collectorName coincida con el nombre buscado
        validateTextView(R.id.collectorName, collectorName)

        //Validamos el textView collectorEmail coincida con el email buscado
        validateTextView(R.id.collectorEmail, collectorEmailToSearch)

        //Validamos el textView collectorPhone coincida con el telefono buscado
        clickIntoButtonByText(R.id.collectorPhone, collectorPhoneToSearch)

        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)

        //Validamos si el fragment_collector_detail es mostrado
        onView(withId(R.id.collector_detail_rv)).check(matches(isDisplayed()))

        //Validamos el textView Name exista
        validateTextView(R.id.collectorName, collectorName)

        //Validamos el textView collectorEmail coincida con el email buscado
        validateTextView(R.id.collectorEmail, collectorEmailToSearch)

        //Validamos el textView collectorPhone coincida con el email buscado
        validateTextView(R.id.collectorPhone, collectorPhoneToSearch)

        //Validamos el textView  coincida con el listado de albumes del Coleccionista
        validateTextView(R.id.albums_title_tv, "Albumes Principales")

        //Validamos si La images es desplegada es mostrado
        onView(withId(R.id.collectorImage)).check(matches(isDisplayed()))

        //Validamos si el listado de Albumes es mostrado
        validateTextView(R.id.AlbumName, "Meteora")

        //Damos click en back
        Espresso.pressBack()

        //Validamos que al darle volver nos lleve nuevamente al listado de Coleccionistas
        onView(withId(R.id.fragment_collector)).check(matches(isDisplayed()))

        //Damos click en back
        Espresso.pressBack()

        //Validamos que al darle volver nos lleve nuevamente a la ventana de Usuario
        onView(withId(R.id.fragment_user)).check(matches(isDisplayed()))
    }
}