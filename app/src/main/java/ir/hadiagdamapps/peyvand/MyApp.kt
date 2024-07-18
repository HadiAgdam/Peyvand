package ir.hadiagdamapps.peyvand

import android.app.Application
import java.io.File
import java.io.FileOutputStream

class MyApp : Application() {

    private val cacheDirs = listOf("user")

    private fun loadPlaceHolder() {
        val f = File(cacheDir, "user/picture.png")
        if (!f.exists())
        {
            val input = assets.open("picture_placeholder.png")
            val output = FileOutputStream(f)

            output.write(input.readBytes())
        }
    }

    private fun makCacheDirs() {
        for (dir in cacheDirs)
            File(cacheDir, dir).mkdir()
    }


    override fun onCreate() {
        super.onCreate()
        makCacheDirs()
        loadPlaceHolder()
    }

}