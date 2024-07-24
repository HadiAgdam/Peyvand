package ir.hadiagdamapps.peyvand.register

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import ir.hadiagdamapps.peyvand.tools.Picture
import ir.hadiagdamapps.peyvand.tools.Profile

class RegisterPagerAdapter(
    private val pager: ViewPager2,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val end: (
        profile: Profile
    ) -> Unit,
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val nameFragment = NameAndPictureFragment(fragmentManager,)
    private val telFragment = TelFragment()
    private val bioFragment = BioFragment()

    override fun getItemCount() = 3

    override fun createFragment(position: Int) = when (position) {
        0 -> nameFragment
        1 -> telFragment
        2 -> bioFragment
        else -> Fragment()
    }

    private fun page() {
        if (pager.currentItem < 2)
            pager.currentItem++
        else if (pager.currentItem == 2) {
            val p = Profile(
                nameFragment.name!!,
                nameFragment.picture,
                telFragment.tel!!,
                bioFragment.bio!!
            )
            end(p)
        }

    }

    fun next() {
        when (pager.currentItem) {

            // Name and Picture
            0 -> if (nameFragment.name != null) page()

            // Tel
            1 -> if (telFragment.tel != null) page()

            // Biography
            2 -> if (bioFragment.bio != null) page()
        }
    }

    fun back() {
        if (pager.currentItem > 0)
            pager.currentItem--
    }

}