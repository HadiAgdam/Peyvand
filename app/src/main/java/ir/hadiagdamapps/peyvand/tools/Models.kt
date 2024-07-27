package ir.hadiagdamapps.peyvand.tools

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import ir.hadiagdamapps.peyvand.R
import kotlinx.parcelize.Parcelize
import org.json.JSONObject
import java.io.File
import java.net.URLEncoder

data class Profile(
    var name: Name,
    var picture: Picture?,
    var tel: Tel,
    var bio: Bio
) {
    fun toQrString(profileHelper: ProfileHelper): String {
        val nameEncoded = URLEncoder.encode(name.toString(), "UTF-8")
        val pictureEncoded = URLEncoder.encode(profileHelper.getPictureUrl(), "UTF-8")
        val telEncoded = URLEncoder.encode(tel.toString(), "UTF-8")
        val bioEncoded = URLEncoder.encode(bio.toString(), "UTF-8")

        val result = "${Constants.TARGET_SERVER}?name=$nameEncoded&picture=$pictureEncoded&tel=$telEncoded&bio=$bioEncoded"

        return result
    }
}

// TODO add location on map maybe

@Parcelize
class Tel private constructor(private val tell: String) : Parcelable {

    override fun toString() = tell

    companion object {
        fun parse(text: String?): Tel? {
            return Tel(TextValidator.validateTel(text ?: return null) ?: return null)
        }
    }

}

@Parcelize
class Picture private constructor(private val bitmap: Bitmap) : Parcelable {

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

@Parcelize
class Name private constructor(private val name: String) : Parcelable {

    companion object {
        fun parse(text: String): Name? {
            return Name(TextValidator.validateName(text) ?: return null)
        }
    }

    override fun toString() = name

}

@Parcelize
class Bio private constructor(private val bio: String) : Parcelable {
    companion object {
        fun parse(text: String?): Bio? {
            return Bio(TextValidator.validateBio(text ?: return null) ?: return null)
        }
    }

    override fun toString() = bio
}

@Parcelize
class Contact(
    val id: Int,
    var name: Name,
    var picture: Picture?,
    var tel: Tel,
    var bio: Bio,
) : Parcelable