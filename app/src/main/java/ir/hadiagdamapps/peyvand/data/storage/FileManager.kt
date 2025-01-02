package ir.hadiagdamapps.peyvand.data.storage

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import java.io.OutputStream

class FileManager(private val contentResolver: ContentResolver) {

    fun saveBitmapToUri(bitmap: Bitmap, uri: Uri) {
        val outputStream: OutputStream = contentResolver.openOutputStream(uri)!!

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

        outputStream.close()
    }


}