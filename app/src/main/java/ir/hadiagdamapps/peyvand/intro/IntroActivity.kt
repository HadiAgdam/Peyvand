package ir.hadiagdamapps.peyvand.intro

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.Activity

class IntroActivity : Activity(R.layout.activity_intro) {

    private val skipButton: Button by lazy { findViewById(R.id.skipButton) }
    private val nextButton: Button by lazy { findViewById(R.id.nextButton) }
    private val pager: ViewPager2 by lazy { findViewById(R.id.pager) }
    private val tab: TabLayout by lazy { findViewById(R.id.tab) }



    private fun end() {
        Toast.makeText(this@IntroActivity, "end", Toast.LENGTH_SHORT).show()
        Log.e("end", "end")
    }


    override fun initViews() {
        val adapter = IntroPagerAdapter(this, supportFragmentManager, lifecycle)
        pager.adapter = adapter
        TabLayoutMediator(tab, pager) { _, _ ->
        }.attach()
    }

    override fun main() {
        nextButton.setOnClickListener {
            if (pager.currentItem < 2)
                pager.currentItem++
            else end()
        }
        skipButton.setOnClickListener { end() }
    }

}