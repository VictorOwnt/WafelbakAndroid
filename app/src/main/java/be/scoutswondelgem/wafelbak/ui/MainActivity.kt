package be.scoutswondelgem.wafelbak.ui

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.koin.android.ext.android.get


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //Injecteren:
    private val sharedPreferences: SharedPreferences = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR

        // Check is user is logged in
        if (!sharedPreferences.getBoolean("ISLOGGEDIN", false)) {
            // Open AuthActivity
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }
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
        toggle.syncState()

        // Setup navHeader
        val headerView = nav_view.getHeaderView(0)
        val navHeaderImage = headerView.nav_header_image //TODO image nodig?
        val navHeaderName = headerView.nav_header_name
        val navHeaderEmail = headerView.nav_header_email
        /*
        // Load image
        val imgUrl = sharedPreferences.getString(SharedPreferencesEnum.IMGURL.string, "")!!
        val img = try {
            FirebaseStorage.getInstance().reference.child(imgUrl)
        } catch (e: Exception) {
            when(e) {
                is StorageException, is IllegalArgumentException -> null
                else -> throw e
            }
        }
        GlideApp.with(this)
            .load(img)
            .placeholder(R.drawable.ic_face)
            .fallback(R.drawable.ic_face)
            .fitCenter()
            .circleCrop()
            .into(navHeaderImage)
         */
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

        // Set logger
        Logger.addLogAdapter(AndroidLogAdapter())


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button.
        when (item.itemId) {
           /* R.id.action_actie2 -> {
                // Get date to show


                // Show datepicker dialog
                val datePickerDialog = DatePickerDialog(this, R.style.DialogTheme, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    // Change dateSelector to selected date
                    val date = DateTime(year, monthOfYear+1, dayOfMonth, 0, 0, 0)
                    this.year = year
                    this.month = monthOfYear
                    this.day = dayOfMonth
                    if (::clickedUser.isInitialized) {
                        openDetailFragment(
                            DateSelectorFragment.newInstance(
                                date,
                                clickedUser.id
                            )
                        )
                    } else {
                        openDetailFragment(
                            DateSelectorFragment.newInstance(
                                date,
                                sharedPreferences.getString(SharedPreferencesEnum.ID.string, "")!!
                            )
                        )
                    }

                }, year, month, day)
                datePickerDialog.show()
            }*/
            R.id.action_allOrders -> {      //TODO only visible for admins
            /*
                setContentView(R.layout.user_list)

                fillListView()
                main_content_container.visibility = View.GONE
             */
                // Logout
                sharedPreferences.edit().clear().apply()
                // Open AuthActivity
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        return true

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        // Handle navigation view item clicks.

        when (item.itemId) {
            R.id.nav_orders -> {
                openDetailFragment(
                OrderFragment.newInstance(sharedPreferences.getString("ID", "")!!))
            }
            R.id.nav_edit_profile -> {
            // Logout
            sharedPreferences.edit().clear().apply()
            // Open AuthActivity
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }
            R.id.nav_logout -> {
                // Logout
                sharedPreferences.edit().clear().apply()
                // Open AuthActivity
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openDetailFragment(newFragment: Fragment) {
        this.supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_content_container, newFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()

    }
}


