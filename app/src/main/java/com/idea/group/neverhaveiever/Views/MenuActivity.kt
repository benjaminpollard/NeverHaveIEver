package com.idea.group.neverhaveiever.Views

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.idea.group.neverhaveiever.R

public class MenuActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener, IMenuHost
{
    var drawer: DrawerLayout? = null
    var fragmentHolder : FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        drawer = findViewById(R.id.drawer_layout)
        fragmentHolder = findViewById(R.id.fragment_holder);
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_holder, NeverHaveIEverFragment.newInstance(""), "")
            .addToBackStack("")
            .commit()
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMenu() {
        drawer!!.openDrawer(Gravity.LEFT)
    }
}