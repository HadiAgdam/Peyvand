package ir.hadiagdamapps.peyvand

import android.app.Application
import java.io.File

class MyApp : Application() {

    private val cacheDirs = listOf("user")

    private fun makCacheDirs() {
        for (dir in cacheDirs)
            File(cacheDir, dir).mkdir()
    }


    override fun onCreate() {
        super.onCreate()
        makCacheDirs()
    }

}