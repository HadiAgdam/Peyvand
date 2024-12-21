package ir.hadiagdamapps.peyvand.tools

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.io.File
import java.net.URL

@Parcelize
class Picture private constructor(private val bitmap: Bitmap?) : Parcelable {

    @IgnoredOnParcel
    var urlString: String? = null

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
            if (url == "") return null

            val bitmap = try {
                BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
            } catch (ex: Exception) {
                null
            }

            return Picture(bitmap).apply { urlString = url }
        }

        fun parse(bitmap: Bitmap): Picture {
            return Picture(bitmap)
        }

    }

    fun toBitmap(): Bitmap? {
        return bitmap
    }

    override fun toString(): String {
        return urlString ?: "null"
    }

}