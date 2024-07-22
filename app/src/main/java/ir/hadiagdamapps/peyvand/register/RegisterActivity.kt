package ir.hadiagdamapps.peyvand.register

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.Activity
import ir.hadiagdamapps.peyvand.tools.Profile
import ir.hadiagdamapps.peyvand.tools.ProfileHelper

class RegisterActivity : Activity(R.layout.activity_register) {

    private val pager: ViewPager2 by lazy { findViewById(R.id.pager) }
    private val nextButton: Button by lazy { findViewById(R.id.nextButton) }
    private val backButton: Button by lazy { findViewById(R.id.backButton) }
    private val adapter by lazy {
        RegisterPagerAdapter(pager, supportFragmentManager, lifecycle) { profile: Profile ->
            ProfileHelper(this@RegisterActivity).setProfile(profile)
            // TODO start MainActivity and finish
        }
    }


    override fun initViews() {
        pager.adapter = adapter
        nextButton.setOnClickListener { adapter.next() }
        backButton.setOnClickListener { adapter.back() }
        pager.isUserInputEnabled = false

    }

    override fun main() {

    }
}