package be.scoutswondelgem.wafelbak.ui

import android.view.Gravity
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import be.scoutswondelgem.wafelbak.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class/*RobolectricTestRunner::class*/)
class UserTest {

    private val util = UtilTestMethods()
    @Rule
    @JvmField
    val rule: ActivityTestRule<MainActivity> = ActivityTestRule((MainActivity::class.java))

    @Before
    fun setup() {
        util.logout()
    }

    @Test
    fun register() {
        onView(withId(R.id.button_register)).perform(scrollTo(), click())
        onView(withId(R.id.input_email)).perform(typeText("client2@gmail.com"))
        onView(withId(R.id.input_password)).perform(scrollTo(),typeText("test00##"))
        onView(withId(R.id.input_verify_password)).perform(scrollTo(),typeText("test00##"))
        onView(withId(R.id.input_firstName)).perform(scrollTo(),typeText("Klaas"))
        onView(withId(R.id.input_lastName)).perform(typeText("Baas"))
        onView(withId(R.id.input_birthday)).perform(scrollTo(),typeText("2020/01/01"))
        onView(withId(R.id.input_street)).perform(scrollTo(),typeText("Staatstraat"))
        onView(withId(R.id.input_streetNumber)).perform(typeText("88"))
        onView(withId(R.id.input_postalCode)).perform(scrollTo(),typeText("9000"))
        onView(withId(R.id.input_city)).perform(scrollTo(),typeText("Stad"))
        onView(withId(R.id.button_register)).perform(scrollTo(),click())
    }

}