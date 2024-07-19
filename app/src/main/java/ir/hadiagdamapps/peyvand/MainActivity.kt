package ir.hadiagdamapps.peyvand

import android.content.Intent
import androidx.navigation.ui.setupWithNavController

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ir.hadiagdamapps.peyvand.intro.IntroActivity
import ir.hadiagdamapps.peyvand.tools.Activity
import ir.hadiagdamapps.peyvand.tools.ProfileHelper

class MainActivity : Activity(R.layout.activity_main) {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var bottomNavigationView: BottomNavigationView

    private val profileHelper by lazy {
        ProfileHelper(this)
    }

    override fun initViews() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavigationView = findViewById(R.id.bottomNav)

        bottomNavigationView.setupWithNavController(navController)
    }

    override fun main() {
        startActivity(Intent(this, IntroActivity::class.java))
        finish()
        return

        val profile = profileHelper.getProfile()
        if (profile == null) {
            if (profileHelper.isFirstLaunch())
                TODO("Show Intro")
            else
                TODO("Show Set Profile")
            finish()
        }
    }
}