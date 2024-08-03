package ir.hadiagdamapps.peyvand

import android.app.Application
import android.content.res.Configuration
import ir.hadiagdamapps.peyvand.tools.ProfileHelper
import java.io.File
import java.io.FileOutputStream
import java.util.Locale

class MyApp : Application() {

    private val cacheDirs = listOf("user")

    private fun setLocale() {
        val locale = Locale(getDeviceLanguage())
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        this.resources.updateConfiguration(config, this.resources.displayMetrics)
    }

    private fun getDeviceLanguage(): String {
        return this.resources.configuration.locale.language
    }

    private fun loadPlaceHolder() {
//        val f = File(cacheDir, "user/picture.png")
//        if (!f.exists())
//        {
//            val input = assets.open("picture_placeholder.png")
//            val output = FileOutputStream(f)
//
//            output.write(input.readBytes())
//        }
    }

    private fun makCacheDirs() {
        for (dir in cacheDirs)
            File(cacheDir, dir).mkdir()
    }


    override fun onCreate() {
        super.onCreate()
        makCacheDirs()
        loadPlaceHolder()
        ProfileHelper(this).resumeUploading()
        setLocale()
    }

}