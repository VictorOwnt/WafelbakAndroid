package be.scoutswondelgem.wafelbak.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import be.scoutswondelgem.wafelbak.R
import com.google.android.material.navigation.NavigationView
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.koin.android.ext.android.get


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //Injecteren:
    private val sharedPreferences: SharedPreferences = get()

    override fun onCreate(savedInstanceState: Bundle?) {        //hierin invokeRepositoryToCallApi
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR

        // Check is user is logged in
        if (!sharedPreferences.getBoolean("ISLOGGEDIN", false)) {
            // Open AuthActivity
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }
        title = "Mijn Bestellingen"
        setContentView(R.layout.activity_main)

        //Toolbar menu
        setSupportActionBar(toolbar)

        //MenuDrawer
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState() //disabling backbutton , enabling hamburger

        // Setup navHeader
        val headerView = nav_view.getHeaderView(0)
        val navHeaderName = headerView.nav_header_name
        val navHeaderEmail = headerView.nav_header_email

        // Load name
        navHeaderName.text = (sharedPreferences.getString("FIRSTNAME", getString(R.string.app_name)) + " " +
                sharedPreferences.getString("LASTNAME", ""))
        // Load email
        navHeaderEmail.text = sharedPreferences.getString("EMAIL", "")

        nav_view.setNavigationItemSelectedListener(this)
        if (savedInstanceState == null) {
            // Check the first item in the navigation menu
            nav_view.menu.findItem(R.id.nav_orders).isChecked = true
            nav_view.menu.performIdentifierAction(R.id.nav_orders, 0)
        }
        if (sharedPreferences.getString("ROLE", "user") != "admin") {
            val item = nav_view.menu.findItem(R.id.nav_all_orders)
            item.isVisible = false
        }

        // Set logger
        Logger.addLogAdapter(AndroidLogAdapter())


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE) //terugkeren
        // Handle navigation view item clicks.
        item.isChecked = true
        when (item.itemId) {
            R.id.nav_orders -> {
                nav_view.menu.findItem(R.id.nav_all_orders).isChecked = false
                nav_view.menu.findItem(R.id.nav_edit_profile).isChecked = false
                toolbar.title = "Mijn Bestellingen"
                openDetailFragment(OrderFragment.newInstance(), "OrderFragment")
            }
            /*R.id.nav_all_orders -> {
                nav_view.menu.findItem(R.id.nav_orders).isChecked = false
                nav_view.menu.findItem(R.id.nav_edit_profile).isChecked = false
                toolbar.title = "Alle Bestellingen"
                openDetailFragment(AllOrderFragment.newInstance(), "AllOrderFragment")
            }
            R.id.nav_edit_profile -> {
                nav_view.menu.findItem(R.id.nav_all_orders).isChecked = false
                nav_view.menu.findItem(R.id.nav_orders).isChecked = false
                toolbar.title = "Accountgegevens Aanpassen"
                openDetailFragment(EditProfileFragment.newInstance(), "EditProfileFragment")
            }
            R.id.nav_logout -> {
                // Logout
                sharedPreferences.edit().clear().apply()
                // Open AuthActivity
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }*/
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            val index = this.supportFragmentManager.backStackEntryCount -1
            val backEntry = supportFragmentManager.getBackStackEntryAt(index)
            val tag = backEntry.name
            when(tag)
            {
                "OrderFragment" -> {
                    toolbar.title = "Mijn Bestellingen"
                    nav_view.menu.findItem(R.id.nav_all_orders).isChecked = false
                    nav_view.menu.findItem(R.id.nav_edit_profile).isChecked = false
                    nav_view.menu.findItem(R.id.nav_orders).isChecked = true
                }
                /*"AllOrderFragment" -> {
                    toolbar.title = "Alle Bestellingen"
                    nav_view.menu.findItem(R.id.nav_all_orders).isChecked = true
                    nav_view.menu.findItem(R.id.nav_edit_profile).isChecked = false
                    nav_view.menu.findItem(R.id.nav_orders).isChecked = false
                }
                "EditProfileFragment" -> {
                    toolbar.title = "Accountgegevens Aanpassen"
                    nav_view.menu.findItem(R.id.nav_all_orders).isChecked = false
                    nav_view.menu.findItem(R.id.nav_edit_profile).isChecked = true
                    nav_view.menu.findItem(R.id.nav_orders).isChecked = false
                }*/
            }
        }
    }

    fun hideKeyboard() {
        val inputManager = this
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // check if no view has focus:
        val currentFocusedView = this.currentFocus
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    private fun openDetailFragment(newFragment: Fragment, tag: String) {
        this.supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_content_container, newFragment, tag)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(tag)
            .commit()

    }
}


