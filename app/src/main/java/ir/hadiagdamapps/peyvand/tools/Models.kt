package ir.hadiagdamapps.peyvand.tools

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import ir.hadiagdamapps.peyvand.R
import org.json.JSONObject
import java.io.File

data class Profile(
    var name: Name,
    var picture: Picture?,
    var tel: Tel,
    var bio: Bio
) {
    fun toJson(): JSONObject {

        return JSONObject()
    }
}

// TODO add location on map maybe


class Tel private constructor(private val tell: String) {

    override fun toString() = tell

    companion object {
        fun parse(text: String?): Tel? {
            return Tel(TextValidator.validateTel(text ?: return null) ?: return null)
        }
    }

}

class Picture private constructor(private val bitmap: Bitmap) {

    companion object {
        fun parse(file: File): Picture? {
            return Picture(BitmapFactory.decodeFile(file.path) ?: return null)
        }

        fun parse(contentResolver: ContentResolver, uri: Uri): Picture? {
            return try {
                Picture(MediaStore.Images.Media.getBitmap(contentResolver, uri))
            } catch (ex: Exception) {
                null
            }
        }

        fun parse(url: String): Picture? {
            TODO("download the image from web and cache it")
        }

        fun getPlaceHolder(context: Context): Bitmap {
            return BitmapFactory.decodeResource(context.resources, R.drawable.picture_placeholder)
//            TODO("return an 'person image' maybe select it from drawable ")
        }
    }

    fun toBitmap(): Bitmap {
        return bitmap
    }

}

class Name private constructor(private val name: String) {

    companion object {
        fun parse(text: String): Name? {
            return Name(TextValidator.validateName(text) ?: return null)
        }
    }

    override fun toString() = name

}

class Bio private constructor(private val bio: String) {
    companion object {
        fun parse(text: String?): Bio? {
            return Bio(TextValidator.validateBio(text ?: return null) ?: return null)
        }
    }

    override fun toString() = bio
}


class Contact(
    val id: Int,
    var name: Name,
    var picture: Picture?,
    var tel: Tel,
    var bio: Bio,
) {

}