package com.idea.group.neverhaveiever.Views

import android.annotation.SuppressLint
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        drawer = findViewById(R.id.drawer_layout)
        fragmentHolder = findViewById(R.id.fragment_holder);
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        changeFragment(CategorySelectorFragment.newInstance())
        setUpMenuItems()
    }

    private fun setUpMenuItems() {

        val teenButton : Button = findViewById(R.id.menu_item_teen)
        teenButton.setOnClickListener{
            GoToIHaveNeverScreen(NeverHaveIEverFragment.CARDS_TEEN)
        }

        val adultButton : Button = findViewById(R.id.menu_item_adult)
        adultButton.setOnClickListener{
            GoToIHaveNeverScreen(NeverHaveIEverFragment.CARDS_ADULT)
        }

        val settingsButton  : Button = findViewById(R.id.menu_item_settings)
        settingsButton.setOnClickListener{
            GoToIHaveNeverScreen(NeverHaveIEverFragment.CARDS_TEEN)
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
        changeFragment(NeverHaveIEverFragment.newInstance(screen))
    }

    private fun changeFragment(fragment: Fragment)
    {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_holder, fragment, "")
            .addToBackStack("")
            .commit()
    }
}