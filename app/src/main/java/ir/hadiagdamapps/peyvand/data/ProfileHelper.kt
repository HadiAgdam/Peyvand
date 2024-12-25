package ir.hadiagdamapps.peyvand.data

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.storage.FirebaseStorage
import ir.hadiagdamapps.peyvand.data.models.profile.Profile
import ir.hadiagdamapps.peyvand.tools.Bio
import ir.hadiagdamapps.peyvand.tools.Name
import ir.hadiagdamapps.peyvand.tools.Picture
import ir.hadiagdamapps.peyvand.tools.Tel
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URLEncoder
import kotlin.random.Random

class ProfileHelper(private val context: Context) {

    private val profilePictureFile = File(context.cacheDir, "user/picture.png")
    private val preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)
    private val ref = FirebaseStorage.getInstance().reference.child("profile_pictures/")


    private fun getImageKey(): String {
        var key = preferences.getString("image_key", null)

        if (key == null) {
            key = ""
            for (i in 0 until 12)
                key += Random.nextInt(0, 10)
        }

        return key
    }

    fun getProfile(): Profile? {
        val name = Name.parse(preferences.getString("name", null) ?: return null)
        val picture = Picture.parse(profilePictureFile)
        val tel = Tel.parse(preferences.getString("tell", null) ?: return null)
        val bio = Bio.parse(preferences.getString("bio", null) ?: return null)

        if (name == null || tel == null || bio == null)
            return null

        return Profile(name, picture, tel, bio)
    }

    fun setName(name: Name) {
        preferences.edit().apply {
            putString("name", name.toString())
            apply()
        }
    }

    fun setTel(tel: Tel) {
        preferences.edit().apply {
            putString("tell", tel.toString())
            apply()
        }
    }

    fun setBio(bio: Bio) {
        preferences.edit().apply {
            putString("bio", bio.toString())
            apply()
        }
    }

    fun resumeUploading() {
        try {
            if (!preferences.getBoolean("image_uploaded", true)) {
                val imageRef = ref.child(getImageKey() + ".png")

                imageRef.putBytes(profilePictureFile.readBytes()).addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener {
                        preferences.edit().apply {
                            putBoolean("image_uploaded", true)
                            putString("image_url", it.toString())
                            apply()
                        }
                    }

                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setPicture(picture: Picture) {
        val ous = ByteArrayOutputStream()
        (picture.toBitmap() ?: return).compress(Bitmap.CompressFormat.PNG, 100, ous)

        profilePictureFile.outputStream().use {
            ous.writeTo(it)
        }

        preferences.edit().apply {
            putBoolean("image_uploaded", false)
            apply()
        }

        resumeUploading()
    }

    fun deletePicture() {
        val imageRef = ref.child(getImageKey() + ".png")

        imageRef.delete()

        profilePictureFile.delete()
        preferences.edit().apply {
            putString("image_url", "")
            apply()
        }
    }

    fun getPictureUrl() = preferences.getString("image_url", "")

    fun isFirstLaunch() = preferences.getBoolean("firstLaunch", true)

    fun disableFirstLaunch() {
        preferences.edit().apply {
            putBoolean("firstLaunch", false)
            apply()
        }
    }

    fun toQrString(profile: Profile): String {
        profile.apply {
            val nameEncoded = URLEncoder.encode(name.toString(), "UTF-8")
            val pictureEncoded = URLEncoder.encode(this@ProfileHelper.getPictureUrl(), "UTF-8")
            val telEncoded = URLEncoder.encode(tel.toString(), "UTF-8")
            val bioEncoded = URLEncoder.encode(bio.toString(), "UTF-8")

            return "${Api.BASE_URL}?${Key.NAME}=$nameEncoded&${Key.PICTURE}=$pictureEncoded&${Key.TEL}=$telEncoded&${Key.BIO}=$bioEncoded${keyManager.getPublicKey()?.let { "&${Key.PUBLIC_KEY}=$it" } ?: ""}"
        }
    }

}