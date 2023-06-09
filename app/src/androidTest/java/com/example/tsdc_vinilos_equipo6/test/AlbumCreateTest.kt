package com.example.tsdc_vinilos_equipo6.test

import android.os.SystemClock
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.tsdc_vinilos_equipo6.R
import com.example.tsdc_vinilos_equipo6.ui.MainActivity
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.endsWith
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class AlbumCreateTest {

    @Rule
    @JvmField
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)


    //Constante que define el tiempo de espera para que se carguen los datos retornados por el adapter
    val delayService = Integer.toUnsignedLong(5000)
    val delayService2 = Integer.toUnsignedLong(1000)

    fun clickIntoButtonById(idView: Int) {
        //Damos click en el boton idView
        onView(withId(idView)).perform(click())
    }

    fun clickIntoButtonByText(idView: Int, valueToSearch: String) {
        //Damos click en el boton idView
        getTextViewByValue(idView, valueToSearch)?.perform(click())
    }

    fun getTextViewByValue(idView: Int, valueToSearch: String): ViewInteraction? {
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        return onView(
            allOf(
                withId(idView),
                ViewMatchers.withText(valueToSearch)
            )
        )
    }

    fun setTextLayoutViewByValue(idView: Int, valueToType:String) {
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        onView(
            allOf(
                isDescendantOfA(withId(idView)),
                withClassName(endsWith("EditText"))
            )
        ).perform(
            typeText(valueToType)
        )
    }

    fun setTextViewByValue(idView: Int, valueToType:String) {
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        onView(
            allOf(
                withId(idView)
            )
        ).perform(
            click(),
            typeText(valueToType),
            closeSoftKeyboard()
        )
    }

    /**
     * Esta prueba tiene como objetivo crear un nuevo album
     */
    @Test
    fun navegateToListAlbums() {
        //Click en Menu Usuario
        clickIntoButtonById(R.id.CollectorManuButton)
        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)
        //Click Catalogo de Albumes
        clickIntoButtonById(R.id.fab_add_album)
        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)
        completeForm()
        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)
    }

    fun completeForm(){
        setTextLayoutViewByValue(R.id.name_album_textField,"Album prueba")

        SystemClock.sleep(delayService2)
        setTextViewByValue(R.id.image_album_textField,"https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com.mx%2Fgasrock23%2Fblack-sabbath%2F&psig=AOvVaw3_e-kyCDby8dsJEf2e-c_h&ust=1684554144715000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCJivhKy7gP8CFQAAAAAdAAAAABAE")

        SystemClock.sleep(delayService2)
        setTextViewByValue(R.id.descripcion_album_textField,"Album de prueba")

        SystemClock.sleep(delayService2)
        setTextViewByValue(R.id.date_album_datepicker,"01/01/2000")

        SystemClock.sleep(delayService2)

        onView(withId(R.id.genero_album_spinner)).perform(click())

        SystemClock.sleep(delayService2)

        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Rock"))).perform(click())
        onView(withId(R.id.genero_album_spinner)).check(matches(withSpinnerText(containsString("Rock"))))


        SystemClock.sleep(delayService2)
        onView(withId(R.id.disquera_album_spinner)).perform(click())

        SystemClock.sleep(delayService2)
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Discos Fuentes"))).perform(click())
        onView(withId(R.id.disquera_album_spinner)).check(matches(withSpinnerText(containsString("Discos Fuentes"))))

        SystemClock.sleep(delayService2)
        onView(withId(R.id.album_create_button)).perform(click())
        SystemClock.sleep(10000)


    }
}