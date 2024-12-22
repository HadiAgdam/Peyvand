package ir.hadiagdamapps.peyvand.register

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import ir.hadiagdamapps.peyvand.data.ProfileHelper

class RegisterPagerAdapter(
    context: Context,
    private val pager: ViewPager2,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val end: (
    ) -> Unit,
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val nameFragment = NameAndPictureFragment(fragmentManager)
    private val telFragment = TelFragment()
    private val bioFragment = BioFragment()

    private val helper = ProfileHelper(context)

    override fun getItemCount() = 3

    override fun createFragment(position: Int) = when (position) {
        0 -> nameFragment
        1 -> telFragment
        2 -> bioFragment
        else -> Fragment()
    }

    fun page() {
        when (pager.currentItem) {
            0 -> if (nameFragment.name != null) {
                helper.setName(nameFragment.name!!)
                if (nameFragment.picture != null)
                    helper.setPicture(nameFragment.picture!!)
                pager.currentItem++
            } else nameFragment.displayError()

            1 ->
                if (telFragment.tel != null) {
                    helper.setTel(telFragment.tel!!)
                    pager.currentItem++
                } else telFragment.displayError()

            2 -> {
                if (bioFragment.bio != null) {
                    helper.setBio(bioFragment.bio!!)
                    end()
                }
                else bioFragment.displayError()

            }
        }
    }


    fun back() {
        if (pager.currentItem > 0)
            pager.currentItem--
    }

}