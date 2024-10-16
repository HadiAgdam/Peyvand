package ir.hadiagdamapps.peyvand.tools

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object Clipboard {

    fun copy(context: Context, text: String, label: String = "Peyvand") {
        (context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
            .setPrimaryClip(
                ClipData.newPlainText(label, text)
            )

    }

}