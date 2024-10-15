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
import ir.hadiagdamapps.peyvand.tools.Constants.Companion.TARGET_SERVER
import kotlinx.parcelize.Parcelize
import org.json.JSONObject
import java.io.File
import java.net.URL
import java.net.URLDecoder
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

        val result =
            "${TARGET_SERVER}?name=$nameEncoded&picture=$pictureEncoded&tel=$telEncoded&bio=$bioEncoded"

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
            try {
                val bitmap = BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())

                if (bitmap != null)
                    return Picture(bitmap)
                return null
            }
            catch (ex: Exception) {
                return null
            }
        }

        fun parse(bitmap: Bitmap): Picture {
            return Picture(bitmap)
        }

        fun getPlaceHolder(context: Context): Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.picture_placeholder)
        
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
    val id: Int = -1,
    var name: Name,
    var picture: Picture?,
    var tel: Tel,
    var bio: Bio,
) : Parcelable {


    companion object {

        private fun parseFromHashmap(map: HashMap<String, String>): Contact? {
            return Contact(
                -1,
                Name.parse(map["name"] ?: return null) ?: return null,
                Picture.parse(map["picture"] ?: ""),
                Tel.parse(map["tel"]) ?: return null,
                Bio.parse(map["bio"]) ?: return null
            )
        }

        fun parseFromURL(text: String): Contact? {
            val result = HashMap<String, String>()

            for (i in text.removeRange(0, TARGET_SERVER.length + 1).split("&"))
                result[URLDecoder.decode(i.split("=")[0], "UTF-8")] =
                    URLDecoder.decode(i.split("=")[1], "UTF-8")

            return parseFromHashmap(result)
        }
    }

}