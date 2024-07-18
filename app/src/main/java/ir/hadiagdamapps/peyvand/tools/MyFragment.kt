package ir.hadiagdamapps.peyvand.tools

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class MyFragment(@LayoutRes layout: Int) : Fragment(layout) {

    abstract fun initViews(view: View)

    abstract fun main()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        main()
    }


}