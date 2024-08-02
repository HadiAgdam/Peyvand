package ir.hadiagdamapps.peyvand.tools

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

abstract class Activity(@LayoutRes private val layout: Int) : AppCompatActivity() {

    abstract fun initViews()
    abstract fun main()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        initViews()
        main()
    }

    override fun attachBaseContext(newBase: Context) {
        val locale = Locale(getDeviceLanguage(newBase))
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }

    private fun getDeviceLanguage(context: Context): String {
        return context.resources.configuration.locale.language
    }
}