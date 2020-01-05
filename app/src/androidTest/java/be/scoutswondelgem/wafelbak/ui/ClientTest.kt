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
class ClientTest {

    private val util = UtilTestMethods()
    @Rule
    @JvmField
    val rule: ActivityTestRule<MainActivity> = ActivityTestRule((MainActivity::class.java))

    @Before
    fun setup() {
        util.logout()
        util.loginClient()
    }

    @Test
    fun addOrderWith8wafflesAfter16WithComment() {
        // Click on button
        onView(withId(R.id.button_addOrder)).perform(click())

        // Fill in fields
        onView(withId(R.id.eightWaffles)).perform(click())
        onView(withId(R.id.evening)).perform((click()))
        onView(withId(R.id.input_comment)).perform(typeText("Dit werd gemaakt in een test"))
        closeSoftKeyboard()

        //Creëren
        onView(withId(R.id.button_save)).perform(click())
    }

    @Test
    fun editProfile() {
        //Open nav and select all orders
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_edit_profile))

        //Edit profile (only fields that don't harm login)
        onView(withId(R.id.input_firstName)).perform(clearText(), typeText("Jan"))
        onView(withId(R.id.input_lastName)).perform(clearText(), typeText("Jansens"))
        onView(withId(R.id.input_street)).perform(scrollTo(), clearText(), typeText("Sesamstraat"))
        onView(withId(R.id.input_streetNumber)).perform(clearText(),typeText("666"))
        closeSoftKeyboard()
        onView(withId(R.id.button_editProfile)).perform(scrollTo(), click())
    }
}