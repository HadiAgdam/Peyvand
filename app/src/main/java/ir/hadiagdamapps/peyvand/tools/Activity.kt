package ir.hadiagdamapps.peyvand.tools

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class Activity(@LayoutRes private val layout: Int) : AppCompatActivity() {

    abstract fun initViews()
    abstract fun main()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        initViews()
        main()
    }
}