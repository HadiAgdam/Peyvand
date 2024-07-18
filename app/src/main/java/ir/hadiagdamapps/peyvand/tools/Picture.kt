package ir.hadiagdamapps.peyvand.tools

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

class Picture private constructor(private val bitmap: Bitmap) {

    companion object {
        fun parse(file: File): Picture? {
            return Picture(BitmapFactory.decodeFile(file.path) ?: return null)
        }
    }


    fun toBitmap() : Bitmap {
        return bitmap
    }


}