package ir.hadiagdamapps.peyvand

import android.content.Intent
import androidx.navigation.ui.setupWithNavController

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ir.hadiagdamapps.peyvand.data.network.ApiSingleton
import ir.hadiagdamapps.peyvand.data.network.UserApi
import ir.hadiagdamapps.peyvand.data.storage.KeyManager
import ir.hadiagdamapps.peyvand.intro.IntroActivity
import ir.hadiagdamapps.peyvand.register.RegisterActivity
import ir.hadiagdamapps.peyvand.tools.Activity
import ir.hadiagdamapps.peyvand.tools.Profile
import ir.hadiagdamapps.peyvand.tools.ProfileHelper

class MainActivity : Activity(R.layout.activity_main) {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var bottomNavigationView: BottomNavigationView


    private lateinit var profileHelper: ProfileHelper


    private fun updateKeySet(profile: Profile) {
        val keyManager = KeyManager(this)
        val set = keyManager.getKeySet()
        if (set == null) {
            val api = UserApi(ApiSingleton.getInstance(this).getRequestQueue())
            api.register(profile, {
                keyManager.put(it)
            }, {
                // I'll just ignore it
            })
        }
    }

    override fun initViews() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavigationView = findViewById(R.id.bottomNav)

        bottomNavigationView.setupWithNavController(navController)
    }

    override fun main() {
        profileHelper = ProfileHelper(this)
        val profile = profileHelper.getProfile()
        if (profile == null) {
            if (profileHelper.isFirstLaunch())
                startActivity(Intent(this, IntroActivity::class.java))
            else
                startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        } else
            updateKeySet(profile)
    }
}