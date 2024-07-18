package ir.hadiagdamapps.peyvand.tools

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

class ProfileHelper(private val context: Context) {

    private val profilePictureFile = File(context.cacheDir, "user/user_profile.png")
    private val preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    fun getProfile(): Profile? {
        return Profile(
            preferences.getString("name", null) ?: return null,
            Picture.parse(profilePictureFile)!!,
            Tell(preferences.getString("tell", null) ?: return null),
            preferences.getString("bio", null) ?: return null
        )
    }

    fun setProfile(profile: Profile) {
        preferences.edit().apply {
            putString("name", profile.name)
            putString("tell", profile.tell.toString())
            putString("bio", profile.bio)
            apply()
        }

        setPicture(profile.picture)
    }

    private fun setPicture(picture: Picture) {
        val ous = FileOutputStream(profilePictureFile)
        picture.toBitmap().compress(Bitmap.CompressFormat.PNG, 100, ous)
    }

}