package ir.hadiagdamapps.peyvand.data

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object Clipboard {

    private const val DEFAULT_LABEL = "Peyvand"

    fun copy(context: Context, text: String, label: String = DEFAULT_LABEL) {
        (context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
            .setPrimaryClip(
                ClipData.newPlainText(label, text)
            )

    }

}