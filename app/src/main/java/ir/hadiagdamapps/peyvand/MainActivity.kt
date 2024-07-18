package ir.hadiagdamapps.peyvand

import androidx.navigation.ui.setupWithNavController

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ir.hadiagdamapps.peyvand.tools.Activity

class MainActivity : Activity(R.layout.activity_main) {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun initViews() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavigationView = findViewById(R.id.bottomNav)

        bottomNavigationView.setupWithNavController(navController)
    }

    override fun main() {

    }
}