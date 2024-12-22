package ir.hadiagdamapps.peyvand.tools

import android.os.Parcelable
import ir.hadiagdamapps.peyvand.data.TextValidator
import kotlinx.parcelize.Parcelize

@Parcelize
class Bio private constructor(private val bio: String) : Parcelable {
    companion object {
        fun parse(text: String?): Bio? {
            return Bio(TextValidator.validateBio(text ?: return null) ?: return null)
        }
    }

    override fun toString() = bio
}