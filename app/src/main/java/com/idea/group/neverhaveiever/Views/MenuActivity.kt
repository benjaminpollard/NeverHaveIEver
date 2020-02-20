package com.idea.group.neverhaveiever.Views

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.idea.group.neverhaveiever.R
import com.idea.group.neverhaveiever.Views.Interfaces.IMenuHost


class MenuActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener,
    IMenuHost
{
    var drawer: DrawerLayout? = null
    var fragmentHolder : FrameLayout? = null
    var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        drawer = findViewById(R.id.drawer_layout)
        fragmentHolder = findViewById(R.id.fragment_holder);

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        prefs = getSharedPreferences(this.packageName, MODE_PRIVATE);

        if (prefs!!.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            prefs!!.edit().putBoolean("firstrun", false).apply()
            changeFragment(CategorySelectorFragment.newInstance())
        }else
        {
            if (NeverHaveIEverFragment.CARDS_CLEAN == prefs!!.getString("screen selected",""))
            {
                GoToIHaveNeverScreen(NeverHaveIEverFragment.CARDS_CLEAN)
            }
            else
            {
                GoToIHaveNeverScreen(NeverHaveIEverFragment.CARDS_NAUGHTY)
            }
        }

        setUpMenuItems()
    }

    private fun setUpMenuItems() {

        val teenButton : Button = findViewById(R.id.menu_item_naughty)
        teenButton.setOnClickListener{
            GoToIHaveNeverScreen(NeverHaveIEverFragment.CARDS_NAUGHTY)
            drawer!!.closeDrawer(Gravity.LEFT)
        }

        val adultButton : Button = findViewById(R.id.menu_item_clean)
        adultButton.setOnClickListener{
            GoToIHaveNeverScreen(NeverHaveIEverFragment.CARDS_CLEAN)
            drawer!!.closeDrawer(Gravity.LEFT)
        }

        val exposedButton : Button = findViewById(R.id.menu_item_exposed)
        exposedButton.setOnClickListener{
            GoToIHaveNeverScreen(NeverHaveIEverFragment.CARDS_EXPOSED)
            drawer!!.closeDrawer(Gravity.LEFT)
        }

        val couplesButton : Button = findViewById(R.id.menu_item_couples)
        couplesButton.setOnClickListener{
            GoToIHaveNeverScreen(NeverHaveIEverFragment.CARDS_NAUGHTY)
            drawer!!.closeDrawer(Gravity.LEFT)
        }

        val settingsButton  : Button = findViewById(R.id.menu_item_settings)
        settingsButton.setOnClickListener{
            GoToIHaveNeverScreen(NeverHaveIEverFragment.CARDS_NAUGHTY)
            drawer!!.closeDrawer(Gravity.LEFT)
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        return false
    }

    @SuppressLint("RtlHardcoded")
    override fun showMenu() {
        drawer!!.openDrawer(Gravity.LEFT)
    }

    override fun GoToIHaveNeverScreen(screen: String) {
        prefs!!.edit().putString("screen selected",screen).apply()
        changeFragment(NeverHaveIEverFragment.newInstance(screen))
    }

    private fun changeFragment(fragment: Fragment)
    {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_holder, fragment, "")
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .addToBackStack("")
            .commit()
    }
}