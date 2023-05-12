package com.example.tsdc_vinilos_equipo6.test

import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
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

@RunWith(AndroidJUnit4::class)
class CollectorListTest {

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

    fun validateTextViewNonExistent(idView: Int, valueToSearch: String) {
        //Validamos que no es mostrado algun TextView de tipo idView
        onView(allOf(withId(idView), not(ViewMatchers.isDisplayed())))
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
    fun CheckCollectorsListTest() {
        /*
            Prueba que tiene como objetivo verificar que exista un numero minimo de coleccionistas
         */
        //Constantes que se pueden modificar con base a los criterios deseados
        val collectorsMinimum = 2

        //Navegamos al view de ListCollectors
        navegateToListCollectors()

        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)

        //Validamos que el listado tenga un minimo de coleccionistas
        onView(withId(R.id.fragment_collector)).check(
            CustomAssertions.greaterItem(
                collectorsMinimum
            )
        )
    }

    @Test
    fun ConsultExistentCollectorTest() {
        /*
            Prueba que tiene como objetivo verificar la informaci�n de un coleccionista existente
         */

        //Constantes que se pueden modificar con base a los criterios deseados
        val titleToSearch = "Listado de Colleccionistas"
        val collectorNameToSearch = "Haiber H. Galindo"
        val collectorEmailToSearch = "hgalindo619@gmail.com"
        val collectorPhoneToSearch = "3102178976"

        //Navegamos al view de ListCollectors
        navegateToListCollectors()

        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)

        //Validamos si el fragment_artist es mostrado
        onView(withId(R.id.fragment_collector)).check(matches(ViewMatchers.isDisplayed()))

        //Validamos el textView Title exista
        validateTextView(R.id.collectorTitle, titleToSearch)

        //Validamos el textView collectorName coincida con el nombre buscado
        validateTextView(R.id.collectorName, collectorNameToSearch)

        //Validamos el textView collectorEmail coincida con el email buscado
        validateTextView(R.id.collectorEmail, collectorEmailToSearch)

        //Validamos el textView collectorPhone coincida con el telefono buscado
        clickIntoButtonByText(R.id.collectorPhone, collectorPhoneToSearch)

        //Damos click en back
        pressBack()

        //Validamos que al darle volver nos lleve nuevamente a la ventana de Usuario
        onView(withId(R.id.fragment_user)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun ConsultNonExistentCollectorTest() {
        /*
            Prueba que tiene como objetivo verificar que no existe la informaci�n de un coleccionista especifico en el listado de coleccionistas
         */

        //Constantes que se pueden modificar con base a los criterios deseados
        val titleToSearch = "Listado de Colleccionistas"
        val collectorNameToSearch = "Prueba Coleccionista"
        val collectorEmailToSearch = "coleccionistaprueba@gmail.com"
        val collectorPhoneToSearch = "3000000000"

        //Navegamos al view de ListArtists
        navegateToListCollectors()

        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)

        //Validamos si el fragments_collector es mostrado
        onView(withId(R.id.fragment_collector)).check(matches(ViewMatchers.isDisplayed()))

        //Validamos el textView Title exista
        validateTextView(R.id.collectorTitle, titleToSearch)

        //Validamos el textView collectorName no tiene coincidencias
        validateTextViewNonExistent(R.id.collectorName, collectorNameToSearch)

        //Validamos el textView collectorEmail no tiene coincidencias
        validateTextViewNonExistent(R.id.collectorEmail, collectorEmailToSearch)

        //Validamos el textView collectorEmail no tiene coincidencias
        validateTextViewNonExistent(R.id.collectorPhone, collectorPhoneToSearch)

        //Damos click en back
        pressBack()

        //Validamos que al darle volver nos lleve nuevamente a la ventana de Usuario
        onView(withId(R.id.fragment_user)).check(matches(ViewMatchers.isDisplayed()))
    }

}