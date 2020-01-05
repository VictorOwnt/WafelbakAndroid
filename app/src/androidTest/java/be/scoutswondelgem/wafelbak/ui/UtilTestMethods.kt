package be.scoutswondelgem.wafelbak.ui

import android.view.Gravity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers
import be.scoutswondelgem.wafelbak.R

class UtilTestMethods {

    fun loginAdmin(){
        Espresso.onView(ViewMatchers.withId(R.id.input_email))
            .perform(ViewActions.typeText("admin@gmail.com"))
        Espresso.onView(ViewMatchers.withId(R.id.input_password))
            .perform(ViewActions.scrollTo(), ViewActions.typeText("test00##"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.button_sign_in))
            .perform(ViewActions.click())
    }

    fun loginClient(){
        Espresso.onView(ViewMatchers.withId(R.id.input_email))
            .perform(ViewActions.typeText("client@gmail.com"))
        Espresso.onView(ViewMatchers.withId(R.id.input_password))
            .perform(ViewActions.scrollTo(), ViewActions.typeText("test00##"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.button_sign_in))
            .perform(ViewActions.click())


    }
    fun logout(){
        //Open the menu
        try {
            Espresso.onView(ViewMatchers.withId(R.id.drawer_layout)).perform(DrawerActions.open())
            Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(ViewAssertions.matches(DrawerMatchers.isOpen()))

            Espresso.onView(ViewMatchers.withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_logout))
        }catch(e:Exception){
            //Catch if user is already logged in
        }

    }
}