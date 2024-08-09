package ir.hadiagdamapps.peyvand.intro

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RawRes
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.airbnb.lottie.LottieAnimationView
import ir.hadiagdamapps.peyvand.R
import ir.hadiagdamapps.peyvand.tools.MyFragment

class IntroFragment(
    private val animation: Int,
    private val title: String,
    private val description: String
) : MyFragment(R.layout.fragment_intro) {

    private lateinit var animationView: LottieAnimationView
    private lateinit var titleText: TextView
    private lateinit var descriptionText: TextView
    private var played = false

    private fun playAnimation() {
        if (played) return

        animationView.playAnimation()

        // temp
        if (animation == R.raw.anim1)
            Handler().postDelayed(
                {
                    animationView.pauseAnimation()
                },
                1000
            )


        played = true
    }

    override fun initViews(view: View) {
        animationView = view.findViewById(R.id.animationView)
        titleText = view.findViewById(R.id.title)
        descriptionText = view.findViewById(R.id.description)
    }

    override fun onResume() {
        super.onResume()
        playAnimation()
    }

    override fun main() {
        animationView.setAnimation(animation)
        animationView.repeatCount = 0

        titleText.text = title
        descriptionText.text = description
    }
}