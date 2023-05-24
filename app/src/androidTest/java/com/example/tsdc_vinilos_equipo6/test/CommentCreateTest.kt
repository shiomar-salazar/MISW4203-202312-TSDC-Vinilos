package com.example.tsdc_vinilos_equipo6.test

import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.tsdc_vinilos_equipo6.R
import com.example.tsdc_vinilos_equipo6.ui.MainActivity
import org.hamcrest.CoreMatchers
import org.hamcrest.core.AllOf
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class CommentCreateTest {

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
                ViewMatchers.isDescendantOfA(withId(idView)),
                ViewMatchers.withClassName(CoreMatchers.endsWith("EditText"))
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
     * Esta prueba tiene como objetivo crear un nuevo comentario
     */
    @Test
    fun navegateToCollectorAlbum() {

        val albumNameToSearch = "Awake"
        val comment = "__New_Comment_test__"

        //Click en Menu Usuario
        clickIntoButtonById(R.id.CollectorManuButton)
        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)
        //Damos click en textView con el AlbumName = albumNameToSearch
        clickIntoButtonByText(R.id.AlbumName, albumNameToSearch)
        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)
        //Click boton agregar comentario
        clickIntoButtonById(R.id.fab_add_comment)


        completeForm(comment)
        //Agregamos un tiempo de espera de 5000
        SystemClock.sleep(delayService)
    }

    fun completeForm(comment:String){

        //Validamos si es visible el elemento createCommentTitle
        onView(allOf(withId(R.id.createCommentTitle), ViewMatchers.isDisplayed()))

        //Validamos si es visible el elemento descriptionCommentTitle
        onView(allOf(withId(R.id.descriptionCommentTitle), ViewMatchers.isDisplayed()))

        //Validamos si es visible el elemento valorationCommentTitle
        onView(allOf(withId(R.id.valorationCommentTitle), ViewMatchers.isDisplayed()))

        //Validamos si es visible el elemento saveButton
        onView(allOf(withId(R.id.saveButton), ViewMatchers.isDisplayed()))

        //Validamos si es visible el elemento cancelButton
        onView(allOf(withId(R.id.cancelButton), ViewMatchers.isDisplayed()))

        //Se ingresa comentario
        setTextLayoutViewByValue(R.id.commentTextField,comment)

        SystemClock.sleep(delayService2)
        //Se genera una valoracion para el comentario (rating)
        onView(withId(R.id.commentRating)).perform(click())

        SystemClock.sleep(delayService2)
        // se guarda el nuevo comentrio
        onView(withId(R.id.saveButton)).perform(click())
        SystemClock.sleep(10000)

        //Validamos que si se muestra el listado de comentarios
        onView(allOf(withId(R.id.album_comments_Rv), ViewMatchers.isDisplayed()))

        //Validamos que existe el nuevo comentario en el listado de comentarios
        onView(allOf(withId(R.id.CommentDescription), ViewMatchers.withText(comment)))

    }

}

