package com.example.tsdc_vinilos_equipo6.test

import android.os.SystemClock
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.tsdc_vinilos_equipo6.R
import com.example.tsdc_vinilos_equipo6.ui.MainActivity
import org.hamcrest.CoreMatchers.endsWith
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.AllOf
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
        Espresso.onView(ViewMatchers.withId(idView)).perform(ViewActions.click())
    }

    fun clickIntoButtonByText(idView: Int, valueToSearch: String) {
        //Damos click en el boton idView
        getTextViewByValue(idView, valueToSearch)?.perform(ViewActions.click())
    }

    fun getTextViewByValue(idView: Int, valueToSearch: String): ViewInteraction? {
        //Validamos si existe un TextView de tipo idView con el texto valueToSearch
        return Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(idView),
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
            typeText(valueToType)
        )
    }

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
        //Agregamos un tiempo de espera de 1000
        SystemClock.sleep(delayService2)
        setTextLayoutViewByValue(R.id.image_album_textField,"https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com.mx%2Fgasrock23%2Fblack-sabbath%2F&psig=AOvVaw3_e-kyCDby8dsJEf2e-c_h&ust=1684554144715000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCJivhKy7gP8CFQAAAAAdAAAAABAE")
        //Agregamos un tiempo de espera de 1000
        SystemClock.sleep(delayService2)
        setTextViewByValue(R.id.date_album_datepicker,"01/01/2000")
        //Agregamos un tiempo de espera de 1000
        SystemClock.sleep(delayService2)
        onView(withId(R.id.genero_album_spinner)).perform(click())
        //Agregamos un tiempo de espera de 1000
        SystemClock.sleep(delayService2)
        onData(allOf(withId(R.id.genero_album_spinner),`is`(instanceOf(String::class.java)),
            `is`("Rock"))).perform(click())
        //Agregamos un tiempo de espera de 1000
        SystemClock.sleep(delayService2)

    }
}