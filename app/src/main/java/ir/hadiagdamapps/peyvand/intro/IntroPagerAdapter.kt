package ir.hadiagdamapps.peyvand.intro

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ir.hadiagdamapps.peyvand.R

class IntroPagerAdapter(private val context: Context, fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {

            0 -> IntroFragment(R.raw.anim1, context.getString(R.string.intro_title_1), context.getString(R.string.intro_content_1))
            1 -> IntroFragment(R.raw.anim2, context.getString(R.string.intro_title_2), context.getString(R.string.intro_content_2))
            2 -> IntroFragment(R.raw.anim3, context.getString(R.string.intro_title_3), context.getString(R.string.intro_content_3))

            else -> Fragment()
        }
    }
}