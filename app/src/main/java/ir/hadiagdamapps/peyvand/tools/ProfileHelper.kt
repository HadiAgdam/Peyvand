package ir.hadiagdamapps.peyvand.tools

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

class ProfileHelper(private val context: Context) {

    private val profilePictureFile = File(context.cacheDir, "user/picture.png")
    private val preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    fun getProfile(): Profile? {

        val name = Name.parse(preferences.getString("name", null) ?: return null)
        val picture = Picture.parse(profilePictureFile)
        val tel = Tel.parse(preferences.getString("tell", null) ?: return null)
        val bio = Bio.parse(preferences.getString("bio", null) ?: return null)


        if (name == null || tel == null || bio == null)
            TODO("clear shared preferences")

        return Profile(name, picture, tel, bio)
    }

    fun setProfile(profile: Profile) {
        preferences.edit().apply {
            putString("name", profile.name.toString())
            putString("tell", profile.tel.toString())
            putString("bio", profile.bio.toString())
            apply()
        }

        setPicture(profile.picture)
//        TODO("upload the picture, and some how save it")
    }

    private fun setPicture(picture: Picture?) {
        if (picture == null) return
        val ous = FileOutputStream(profilePictureFile)
        picture.toBitmap().compress(Bitmap.CompressFormat.PNG, 100, ous)
        // TODO("upload the picture")
    }

    fun getPictureUrl(): String {
        TODO("return the profile image url")
    }

    fun isFirstLaunch() = preferences.getBoolean("firstLaunch", true)

    fun disableFirstLaunch() {
        preferences.edit().apply {
            putBoolean("firstLaunch", false)
            apply()
        }
    }

}