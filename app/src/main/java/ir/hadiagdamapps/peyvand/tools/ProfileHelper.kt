package ir.hadiagdamapps.peyvand.tools

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

class ProfileHelper(private val context: Context) {

    private val profilePictureFile = File(context.cacheDir, "user/picture.png")
    private val preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    fun getProfile(): Profile? {
        val p = Profile(
            Name.parse(preferences.getString("name", null) ?: return null) ?: return null,
            Picture.parse(profilePictureFile)!!,
            Tel.parse(preferences.getString("tell", null) ?: return null) ?: return null,
            preferences.getString("bio", null) ?: return null
        )

        if (p == null)
            TODO("clear shared preferences")

        return p
    }

    fun setProfile(profile: Profile) {
        preferences.edit().apply {
            putString("name", profile.name.toString())
            putString("tell", profile.tel.toString())
            putString("bio", profile.bio)
            apply()
        }

        setPicture(profile.picture)
    }

    private fun setPicture(picture: Picture?) {
        if (picture == null) return
        val ous = FileOutputStream(profilePictureFile)
        picture.toBitmap().compress(Bitmap.CompressFormat.PNG, 100, ous)
    }

    fun isFirstLaunch() = preferences.getBoolean("fistLaunch", true)

    fun disableFirstLaunch() {
        preferences.edit().apply {
            putBoolean("firstLaunch", false)
            apply()
        }
    }

}